package ru.tinkoff.tinkoffer.presentation.screen.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.presentation.common.ProjectShort

class ProjectListViewModel : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToProject = MutableSharedFlow<Unit>()
    val navigateToProject: SharedFlow<Unit> = _navigateToProject
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToCreateProject = MutableSharedFlow<Unit>()
    val navigateToCreateProject: SharedFlow<Unit> = _navigateToCreateProject
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        // TODO(Получать список всех проектов)
        println()
    }

    fun onCreateProjectClick() = viewModelScope.launch {
        _navigateToCreateProject.emit(Unit)
    }

    fun onProjectClick(projectShort: ProjectShort) = viewModelScope.launch {
        // TODO(Сделать этот проект активным (послав запрос на бэк))
        _navigateToProject.emit(Unit)
    }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}