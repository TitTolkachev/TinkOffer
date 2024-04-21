package ru.tinkoff.tinkoffer.presentation.screen.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.data.models.users.requests.ChangeAvatarRequest
import ru.tinkoff.tinkoffer.data.models.users.response.UserInfoDto
import ru.tinkoff.tinkoffer.data.rest.UserRestApi

class ProfileViewModel(
    private val userRestApi: UserRestApi,
    private val prefsDataStore: PrefsDataStore
) : ViewModel() {

    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _navigateToSignIn = MutableSharedFlow<Unit>()
    val navigateToSignIn: SharedFlow<Unit> = _navigateToSignIn
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    private val _state = MutableStateFlow<UserInfoDto?>(null)
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val response = userRestApi.getMyUserInfo()
            if (response.isSuccessful){
                response.body()?.let {
                    _state.emit(it)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            prefsDataStore.updateTokens("")
//            val response = userRestApi.logout()
//            if (response.isSuccessful){
                _navigateToSignIn.emit(Unit)
//            }
//            Log.d("Logout", response.toString())

        }
    }

    fun save(avatar: Int) {
        viewModelScope.launch {

            val response = userRestApi.changeAvatar(ChangeAvatarRequest(avatar))
            if (response.isSuccessful){
                _navigateBack.emit(Unit)
            }
            Log.d("Save", response.toString())


        }
    }

    fun navigateBack() = viewModelScope.launch {
        _navigateBack.emit(Unit)
    }
}