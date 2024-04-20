package ru.tinkoff.tinkoffer.presentation.screen.projectusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class ProjectUsersViewModel : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}