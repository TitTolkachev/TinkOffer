package ru.tinkoff.tinkoffer.presentation.screen.projectsettings

import android.util.Log
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
import ru.tinkoff.tinkoffer.data.models.projects.request.EditProjectDto
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi
import ru.tinkoff.tinkoffer.presentation.screen.createproject.CreateProjectState

class ProjectSettingsViewModel(
    savedStateHandle: SavedStateHandle,
    private val projectRestApi: ProjectRestApi
) : ViewModel() {
    private val projectId: String = requireNotNull(savedStateHandle["project_id"])

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
        viewModelScope.launch {
            val response = projectRestApi.getProjectInfo(projectId)
            Log.d(TAG,response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let { currentProjectState ->
                    _state.update {
                        it.copy(
                            name = currentProjectState.name,
                            schedule = currentProjectState.schedule,
                            voices = currentProjectState.votesPerPeriod,
                            refreshDays = currentProjectState.votesRefreshPeriodDays
                        )
                    }
                }

            } else {
                // TOdo show snack

            }
        }
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
            with(state.value) {
                val editModel = EditProjectDto(name, schedule, voices, refreshDays)
                val response = projectRestApi.editProject(projectId, editModel)
                if (response.isSuccessful) {
                    _navigateToProject.emit(Unit)
                    _loading.update { false }
                } else {
                    // TOdo show snack
                }
            }

        }
    }

    companion object{
        val TAG = ProjectSettingsViewModel::class.java.simpleName
    }
}