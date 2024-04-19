package ru.tinkoff.tinkoffer.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _selectedIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

    val fabVisible: StateFlow<Boolean> = _selectedIndex.map {
        when (it) {
            0, 1 -> true
            else -> false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _fabActions = MutableSharedFlow<Unit>()
    val fabActions = _fabActions.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _bottomNavActions = MutableSharedFlow<String>()
    val bottomNavActions = _bottomNavActions.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed()
    )

    fun onBottomNavItemClick(index: Int) {
        _selectedIndex.update { index }
    }

    fun onFabClick() {
        viewModelScope.launch {
            _fabActions.emit(Unit)
        }
    }
}