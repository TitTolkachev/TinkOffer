package ru.tinkoff.tinkoffer.presentation.screen.proposal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.models.comments.request.CreateCommentDto
import ru.tinkoff.tinkoffer.data.models.proposals.request.ChangeProposalStatus
import ru.tinkoff.tinkoffer.data.models.proposals.request.VoteRequest
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInListDto
import ru.tinkoff.tinkoffer.data.models.proposals.response.ProposalInfoDto
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto
import ru.tinkoff.tinkoffer.data.rest.CommentRestApi
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi
import ru.tinkoff.tinkoffer.data.rest.ProposalRestApi
import ru.tinkoff.tinkoffer.presentation.common.ProposalStatus

class ProposalViewModel(
    private val proposalRestApi: ProposalRestApi,
    private val commentRestApi: CommentRestApi,
    private val projectRestApi: ProjectRestApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val proposalId: String = requireNotNull(savedStateHandle["proposal_id"])
    private val projectId: String = requireNotNull(savedStateHandle["project_id"])
    private val isAdmin: Boolean = requireNotNull(savedStateHandle["is_admin"])

    private val _state = MutableStateFlow<CombinedProposal?>(null)
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    private val _parentOfComment = MutableStateFlow<String?>(null)
    val parentOfComment =
        _parentOfComment.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    private val _commentValue = MutableStateFlow<String?>(null)
    val comment = _commentValue.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _activeUserInProposal = MutableStateFlow<List<UserInfoDto>>(listOf())
    val activeUserInProposal =
        _activeUserInProposal.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())


    private val _newComment = MutableStateFlow(true)

    init {
        loadInfo()
    }

    private fun loadInfo() {
        viewModelScope.launch {

                val response = proposalRestApi.getProposalById(proposalId)
                if (response.isSuccessful) {
                    response.body()?.let { infoDto ->
                        _activeUserInProposal.emit(infoDto.usersVotedFor + infoDto.usersVotedAgainst)

                        val nextResponse = projectRestApi.getProposalsByProject(projectId)
                        if (nextResponse.isSuccessful) {
                            nextResponse.body()?.let { inListInfos ->
                                _state.emit(
                                    CombinedProposal(
                                        infoDto,
                                        inListInfos.first { it.id == infoDto.id })
                                )

                            }
                        }

                    }
                } else {
                    // TODO show snack
                }

        }
    }

    fun selectParentOfComment(parentCommentId: String) {
        if (parentCommentId == _parentOfComment.value) {
            _commentValue.update { "" }
            _parentOfComment.update { "" }
            _newComment.update { true }

        } else {
            _parentOfComment.update { parentCommentId }
            _newComment.update { true }

        }
    }

    fun onChangeCommentText(commentText: String) = _commentValue.update { commentText }

    fun onEditCommentClick(commentId: String) {
        _commentValue.update { state.value?.infoDto?.comments?.firstOrNull { it.id == commentId }?.text }
        _newComment.update { false }
    }

    fun onOffEditCommentClick() {
        _commentValue.update { "" }
        _newComment.update { true }
    }

    fun sendComment() {
        viewModelScope.launch {
            if (_newComment.value) {
                if (_parentOfComment.value.isNullOrBlank()) {
                    if (_commentValue.value?.isNotEmpty() == true) {
                        val model = CreateCommentDto(_commentValue.value!!)
                        commentRestApi.createCommentToProposal(proposalId, model)
                        clearTexts()
                        // todo check
                        loadInfo()
                    }
                } else {
                    if (_commentValue.value?.isNotEmpty() == true) {
                        val model = CreateCommentDto(_commentValue.value!!)
                        commentRestApi.createCommentToComment(_parentOfComment.value!!, model)
                        clearTexts()
                        // todo check
                        loadInfo()
                    }
                }
            } else {
                // todo
                // edit
            }
        }
    }

    private fun clearTexts() {
        _commentValue.update { "" }
        _parentOfComment.update { "" }
        _newComment.update { true }
    }

    fun changeStatus(newStatus: ProposalStatus) {
        if (isAdmin) {
            viewModelScope.launch {
                val model = ChangeProposalStatus(newStatus)
                val response = proposalRestApi.changeProposalStatus(proposalId, model)
                if (response.isSuccessful) {
                    loadInfo()
                } else {
                    // TODO show snack
                }
            }
        }

    }


    fun onLikeClick() {
        if (state.value?.infoDto?.canBeVoteCanceled == true) {
            viewModelScope.launch {
                val model = VoteRequest(true)
                val response = proposalRestApi.voteForProposal(proposalId, model)
                if (response.isSuccessful) {
                    loadInfo()
                } else {
                    // TODO show snack
                }
            }
        }
    }

    fun onDislikeClick() {
        if (state.value?.infoDto?.canBeVoteCanceled == true) {
            viewModelScope.launch {
                val model = VoteRequest(false)
                val response = proposalRestApi.voteForProposal(proposalId, model)
                if (response.isSuccessful) {
                    loadInfo()
                } else {
                    // TODO show snack
                }
            }
        }


    }

    fun onDismissVoteClick() {
        if (state.value?.infoDto?.canBeVoteCanceled == true) {
            viewModelScope.launch {
                val response = proposalRestApi.cancelVoteForProposal(proposalId)
                if (response.isSuccessful) {
                    loadInfo()
                } else {
                    // TODO show snack
                }
            }
        }
    }

    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}

data class CombinedProposal(
    val infoDto: ProposalInfoDto,
    val inListData: ProposalInListDto
)