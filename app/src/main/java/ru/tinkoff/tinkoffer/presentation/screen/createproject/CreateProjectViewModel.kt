package ru.tinkoff.tinkoffer.presentation.screen.createproject

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

class CreateProjectViewModel : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToProject = MutableSharedFlow<Unit>()
    val navigateToProject: SharedFlow<Unit> = _navigateToProject
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _state = MutableStateFlow(CreateProjectState())
    val state = _state.asStateFlow()

    init {

    }

    fun changeName(value: String) = _state.update { it.copy(name = value) }
    fun changeSchedule(value: String) = _state.update { it.copy(schedule = value) }
    fun changeVoices(value: Int) = _state.update { it.copy(voices = value) }
    fun changeRefreshDays(value: Int) = _state.update { it.copy(refreshDays = value) }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}

data class CreateProjectState(
    val name: String = "",
    val schedule: String = "",
    val voices: Int = 3,
    val refreshDays: Int = 14,
)