package ru.tinkoff.tinkoffer.presentation.screen.projectusers

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.models.projects.request.UserList
import ru.tinkoff.tinkoffer.data.models.users.response.ProjectUserInfoDto
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi
import ru.tinkoff.tinkoffer.data.rest.UserRestApi

class ProjectUsersViewModel(
    private val projectRestApi: ProjectRestApi,
    private val userRestApi: UserRestApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val projectId: String = requireNotNull(savedStateHandle["project_id"])
    private val userId: String = requireNotNull(savedStateHandle["user_id"])


    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _usersInProject = MutableStateFlow<List<ProjectUserInfoDto>>(listOf())
    val usersInProject =
        _usersInProject.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _usersNotInProject = MutableStateFlow<List<UserInfoDto>>(listOf())
    val usersNotInProject =
        _usersNotInProject.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())


    init {
        loadUsers()
        loadUsersNotInProject()
    }


    fun deleteUser(selectedUserId: String) {
        viewModelScope.launch {
            if (_usersInProject.value.first { it.id == userId }.isAdmin) {
                val response =
                    projectRestApi.deleteUsersFromProject(projectId, listOf(selectedUserId))
                if (response.isSuccessful) {
                    loadUsers()
                    loadUsersNotInProject()
                }
            } else {
                // Todo you cant
            }
        }
    }


    fun upUser(selectedUserId: String) {
        viewModelScope.launch {
            if (_usersInProject.value.first { it.id == userId }.isAdmin) {
                val response = projectRestApi.addAdminRole(projectId, selectedUserId)
                if (response.isSuccessful) {
                    loadUsers()
                    loadUsersNotInProject()
                }
            } else {
                // Todo you cant
            }
        }
    }

    fun downUser(selectedUserId: String) {
        viewModelScope.launch {
            if (_usersInProject.value.first { it.id == userId }.isAdmin) {
                val response = projectRestApi.removeAdminRole(projectId, selectedUserId)
                if (response.isSuccessful) {
                    loadUsers()
                    loadUsersNotInProject()
                }
            } else {
                // Todo you cant
            }
        }
    }


    private fun loadUsersNotInProject() {
        viewModelScope.launch {
            val projectResponse = userRestApi.getUserNotFromSelectedProject(projectId)
            if (projectResponse.isSuccessful) {
                projectResponse.body()?.let {
                    _usersNotInProject.emit(it)
                }
            } else {
                // TODO show snack
            }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            val projectResponse = projectRestApi.getProjectInfo(projectId)
            if (projectResponse.isSuccessful) {
                projectResponse.body()?.let {
                    _usersInProject.emit(it.users)
                }
            } else {
                // TODO show snack
            }
        }
    }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }

    fun addUser(userId: String) {
        viewModelScope.launch {
            val response = projectRestApi.addUserToProject(projectId, UserList(listOf(userId)))
            if (response.isSuccessful) {
                loadUsers()
            } else {
                // todo show snack
            }
        }
    }
}