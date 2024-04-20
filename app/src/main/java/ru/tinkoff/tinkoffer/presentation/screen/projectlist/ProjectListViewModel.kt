package ru.tinkoff.tinkoffer.presentation.screen.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.models.projects.response.ProjectInListDto
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi

class ProjectListViewModel(
    private val projectRestApi: ProjectRestApi
) : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToProject = MutableSharedFlow<Unit>()
    val navigateToProject: SharedFlow<Unit> = _navigateToProject
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToCreateProject = MutableSharedFlow<Unit>()
    val navigateToCreateProject: SharedFlow<Unit> = _navigateToCreateProject
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    private val _availableProjects = MutableStateFlow<List<ProjectInListDto>>(listOf())
    val availableProjects =
        _availableProjects.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch {
            val availableProjectsResponse = projectRestApi.getProjectsByUser()
            if (availableProjectsResponse.isSuccessful){
                availableProjectsResponse.body()?.let {
                    _availableProjects.emit(it)
                }
            } else{
                // TODO show snack
            }
        }
    }

    fun onCreateProjectClick() = viewModelScope.launch {
        _navigateToCreateProject.emit(Unit)
    }

    fun onProjectClick(projectShort: ProjectInListDto) = viewModelScope.launch {
        viewModelScope.launch {
            val response = projectRestApi.activateProject(projectShort.id)
            if (response.isSuccessful){
                _navigateToProject.emit(Unit)
            } else{
                // TODO show snack
            }
        }
    }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}