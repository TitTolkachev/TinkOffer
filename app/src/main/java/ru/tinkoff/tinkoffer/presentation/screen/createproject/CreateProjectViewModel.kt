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
import ru.tinkoff.tinkoffer.data.models.projects.request.CreateProjectDto
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi

class CreateProjectViewModel(
    private val projectRestApi: ProjectRestApi
) : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToProject = MutableSharedFlow<Unit>()
    val navigateToProject: SharedFlow<Unit> = _navigateToProject
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _state = MutableStateFlow(CreateProjectState())
    val state = _state.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()



    init {

    }

    fun changeName(value: String) = _state.update { it.copy(name = value) }
    fun changeSchedule(value: String) = _state.update { it.copy(schedule = value) }
    fun changeVoices(value: Int) = _state.update { it.copy(voices = value) }
    fun changeRefreshDays(value: Int) = _state.update { it.copy(refreshDays = value) }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }

    fun create() = viewModelScope.launch {
        if (_loading.value) return@launch

        _loading.update { true }


        viewModelScope.launch {
            with(state.value){
                val model = CreateProjectDto(name, schedule, voices, refreshDays)
                val response = projectRestApi.createProject(model)
                if (response.isSuccessful){
                    _navigateToProject.emit(Unit)
                    _loading.update { false }
                } else{
                    // TODO show snack
                }
            }
        }
    }
}

data class CreateProjectState(
    val name: String = "",
    val schedule: String = "",
    val voices: Int = 3,
    val refreshDays: Int = 14,
)