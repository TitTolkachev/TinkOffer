package ru.tinkoff.tinkoffer.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.models.ShortProjectInfo

class HomeViewModel : ViewModel() {
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

    private val _projectInfo = MutableStateFlow<ShortProjectInfo?>(null)
    val projectInfo = _projectInfo.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _dialogState = MutableStateFlow<CreateProposalState?>(null)
    val dialogState = _dialogState.asStateFlow()

    fun onBottomNavItemClick(index: Int) {
        _selectedIndex.update { index }
    }

    fun onFabClick() = _dialogState.update { CreateProposalState() }

    fun hideDialog() = _dialogState.update { null }

    fun changeDialogText(value: String) = _dialogState.update { it?.copy(text = value) }
    fun changeDialogLink(value: String) = _dialogState.update { it?.copy(link = value) }

    fun createProposal() = viewModelScope.launch {
        // TODO
        _dialogState.update { null }
        _scrollToIndex.emit(1)
    }
}

data class CreateProposalState(
    val text: String = "",
    val link: String = "",
)