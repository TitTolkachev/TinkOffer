package ru.tinkoff.tinkoffer.presentation.screen.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _navigateToHome = MutableSharedFlow<Unit>()
    val navigateToHome: SharedFlow<Unit> = _navigateToHome
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _success = MutableSharedFlow<String>()
    val success = _success.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _link = MutableSharedFlow<String>()
    val link = _link.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _token: String? = savedStateHandle["token"]

    init {
        if (_token != null) {
            viewModelScope.launch {
                _loading.update { true }

                try {
                    //TODO(Отправить запрос на бэк)
                    delay(1000L)
                    _navigateToHome.emit(Unit)
                } catch (_: Exception) {
                    _error.emit("Не удалось войти")
                }

                _loading.update { false }
            }
        }
    }

    fun onSignInClick() = viewModelScope.launch {
        _link.emit("http://77.106.105.103:8089/")
    }
}