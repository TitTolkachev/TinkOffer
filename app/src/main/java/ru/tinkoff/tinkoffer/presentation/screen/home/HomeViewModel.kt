package ru.tinkoff.tinkoffer.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInfoDto
import ru.tinkoff.tinkoffer.data.models.proposals.request.CreateProposalRequest
import ru.tinkoff.tinkoffer.data.models.proposals.request.VoteRequest
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto
import ru.tinkoff.tinkoffer.data.rest.CommentRestApi
import ru.tinkoff.tinkoffer.data.rest.DraftRestApi
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi
import ru.tinkoff.tinkoffer.data.rest.ProposalRestApi
import ru.tinkoff.tinkoffer.data.rest.UserRestApi
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus

class HomeViewModel(
    private val projectRestApi: ProjectRestApi,
    private val proposalRestApi: ProposalRestApi,
    private val userRestApi: UserRestApi,
    private val draftRestApi: DraftRestApi,
    private val commentRestApi: CommentRestApi,
    private val prefsDataStore: PrefsDataStore
) : ViewModel() {
    private val _selectedIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

    val fabVisible: StateFlow<Boolean> = _selectedIndex.map {
        when (it) {
            0, 1 -> true
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _scrollToIndex = MutableSharedFlow<Int>()
    val scrollToIndex = _scrollToIndex.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed()
    )

    private val _error = MutableSharedFlow<String?>()
    val error = _error.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed()
    )

    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    private val _activeProjectId = MutableStateFlow<String?>(null)

    private val _activeProjectInfo = MutableStateFlow<ProjectInfoDto?>(null)
    val activeProjectInfo =
        _activeProjectInfo.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _newProposals = MutableStateFlow<List<ProposalInListDto>>(listOf())
    private val _inProgressProposals = MutableStateFlow<List<ProposalInListDto>>(listOf())
    private val _acceptedProposals = MutableStateFlow<List<ProposalInListDto>>(listOf())
    private val _rejectedProposals = MutableStateFlow<List<ProposalInListDto>>(listOf())

    val newProposals =
        _newProposals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
    val inProgressProposals =
        _inProgressProposals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
    val acceptedProposals =
        _acceptedProposals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
    val rejectedProposals =
        _rejectedProposals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _dialogState = MutableStateFlow<CreateProposalState?>(null)
    val dialogState = _dialogState.asStateFlow()

    init {
        viewModelScope.launch {
            _userId.emit(prefsDataStore.userIdFlow.first())
            loadActiveProject()
        }

    }

    fun loadActiveProject() {
        viewModelScope.launch {
            val activeProjectIdResponse = projectRestApi.getActiveProject()
            Log.d(TAG, activeProjectIdResponse.body().toString())
            if (activeProjectIdResponse.isSuccessful) {
                activeProjectIdResponse.body()?.id?.let {
                    _activeProjectId.emit(it)
                    val activeProjectInfoResponse = projectRestApi.getProjectInfo(it)
                    Log.d(TAG, activeProjectInfoResponse.body().toString())
                    if (activeProjectInfoResponse.isSuccessful) {
                        _activeProjectInfo.emit(activeProjectInfoResponse.body())
                        loadProposalsForActiveProject()
                    } else {
                        _error.emit(
                            activeProjectInfoResponse.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                        )
                    }
                }
            } else {
                _error.emit(
                    activeProjectIdResponse.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                )
            }
        }
    }

    private fun loadProposalsForActiveProject() {
        viewModelScope.launch {
            _activeProjectId.value?.let {
                val proposalsResponse = projectRestApi.getProposalsByProject(it)
                if (proposalsResponse.isSuccessful) {
                    proposalsResponse.body()?.let { proposals ->
                        _newProposals.emit(proposals.filter { item -> item.proposalStatus == ProposalStatus.NEW })
                        _inProgressProposals.emit(proposals.filter { item -> item.proposalStatus == ProposalStatus.IN_PROGRESS })
                        _acceptedProposals.emit(proposals.filter { item -> item.proposalStatus == ProposalStatus.ACCEPTED })
                        _rejectedProposals.emit(proposals.filter { item -> item.proposalStatus == ProposalStatus.REJECTED })
                    }
                } else {
                    _error.emit(
                        proposalsResponse.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                    )
                }
            }
        }
    }

    fun onBottomNavItemClick(index: Int) {
        _selectedIndex.update { index }
    }

    fun onFabClick() = _dialogState.update { CreateProposalState() }

    fun hideDialog() = _dialogState.update { null }

    fun changeDialogText(value: String) = _dialogState.update { it?.copy(text = value) }
    fun changeDialogLink(value: String) = _dialogState.update { it?.copy(link = value) }

    fun createProposal() = viewModelScope.launch {
        viewModelScope.launch {
            with(dialogState.value) {
                this?.let {
                    val createProposalModel =
                        activeProjectInfo.value?.id?.let { CreateProposalRequest(text, it, link) }
                    if (createProposalModel != null) {
                        val response = proposalRestApi.createProposal(createProposalModel)
                        if (response.isSuccessful) {
                            _dialogState.update { null }
                            _scrollToIndex.emit(1)
                            loadActiveProject()
                        } else {
                            _error.emit(
                                response.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                            )
                        }
                    }
                }
            }
        }
    }

    fun onLikeClick(proposalId: String) {
        viewModelScope.launch {
            val model = VoteRequest(true)
            val response = proposalRestApi.voteForProposal(proposalId, model)
            if (response.isSuccessful) {
                loadProposalsForActiveProject()
            } else {
                _error.emit(
                    response.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                )
            }
        }
    }

    fun onDislikeClick(proposalId: String) {
        viewModelScope.launch {
            val model = VoteRequest(false)
            val response = proposalRestApi.voteForProposal(proposalId, model)
            if (response.isSuccessful) {
                loadProposalsForActiveProject()
            } else {
                _error.emit(
                    response.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                )
            }
        }
    }

    fun onDismissVoteClick(proposalId: String) {
        viewModelScope.launch {
            val response = proposalRestApi.cancelVoteForProposal(proposalId)
            if (response.isSuccessful) {
                loadProposalsForActiveProject()
            } else {
                _error.emit(
                    response.errorBody()?.string()?.substringAfter("[\"")?.substringBefore("\"]")
                )
            }
        }
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }
}

data class CreateProposalState(
    val text: String = "",
    val link: String = "",
)