package ru.tinkoff.tinkoffer.presentation.screen.drafts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi
import ru.tinkoff.tinkoffer.presentation.screen.home.CreateProposalState

class DraftsViewModel(
    savedStateHandle: SavedStateHandle,
    private val projectRestApi: ProjectRestApi
) : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _dialogState = MutableStateFlow<CreateProposalState?>(null)
    val dialogState = _dialogState.asStateFlow()

    private val _drafts = MutableStateFlow<List<String>>(emptyList())
    val drafts = _drafts.asStateFlow()

    init {
        _drafts.update {
            // TODO
            emptyList()
        }
    }

    fun hideDialog() = _dialogState.update { null }
    fun changeDialogText(value: String) = _dialogState.update { it?.copy(text = value) }
    fun changeDialogLink(value: String) = _dialogState.update { it?.copy(link = value) }
    fun createProposal() = viewModelScope.launch {
        // TODO
    }

    fun showDraft(draft: String) = _dialogState.update { CreateProposalState() }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}