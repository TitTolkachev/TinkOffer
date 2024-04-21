package ru.tinkoff.tinkoffer.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToSignIn = MutableSharedFlow<Unit>()
    val navigateToSignIn: SharedFlow<Unit> = _navigateToSignIn
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {

    }

    fun logout() {
        //TODO

        viewModelScope.launch {
            _navigateToSignIn.emit(Unit)
        }
    }

    fun save(avatar: Int) {
        //TODO

        viewModelScope.launch {
            _navigateBack.emit(Unit)
        }
    }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}