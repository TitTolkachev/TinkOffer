package ru.tinkoff.tinkoffer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.presentation.navigation.Screen

class MainViewModel(
    private val prefsDataStore: PrefsDataStore,
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Screen?>(null)
    val startDestination: StateFlow<Screen?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val startDestination = if (true) {
                Screen.SignIn
            } else Screen.Home

            _startDestination.emit(startDestination)
        }
    }
}