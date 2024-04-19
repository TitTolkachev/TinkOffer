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

    private val _activeScreen: MutableStateFlow<HomePage> = MutableStateFlow(START_SCREEN)
    val activeScreen: StateFlow<HomePage> = _activeScreen.asStateFlow()

    val fabVisible: StateFlow<Boolean> = _activeScreen.map {
        when (it) {
            HomePage.ActiveProposals, HomePage.AcceptedProposals -> true
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

    fun onBottomNavItemClick(item: HomePage) {
        viewModelScope.launch {
            _bottomNavActions.emit(item.route)
        }
    }

    fun onActiveScreenChange(route: String?) {
        val screen = SCREENS.find { it.route == route } ?: return
        _activeScreen.update { screen }
    }

    fun onFabClick() {
        viewModelScope.launch {
            _fabActions.emit(Unit)
        }
    }

    companion object {
        val START_SCREEN = HomePage.Main
        val SCREENS = listOf(
            HomePage.Main,
            HomePage.NewProposals,
            HomePage.ActiveProposals,
            HomePage.AcceptedProposals,
            HomePage.RejectedProposals,
        )
    }
}