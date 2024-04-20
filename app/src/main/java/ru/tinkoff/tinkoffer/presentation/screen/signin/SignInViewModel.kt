package ru.tinkoff.tinkoffer.presentation.screen.signin

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.data.models.users.requests.SingInRequest
import ru.tinkoff.tinkoffer.data.rest.UserRestApi

class SignInViewModel(
    savedStateHandle: SavedStateHandle,
    userRestApi: UserRestApi,
    prefsDataStore: PrefsDataStore
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
            Log.d(TAG, _token.toString())
            viewModelScope.launch {
                _loading.update { true }

                try {
                    val model = SingInRequest(_token)
                    val response = userRestApi.singIn(model)
                    Log.d(TAG, response.toString())

                    if (response.isSuccessful) {
                        prefsDataStore.updateTokens(response.body()?.accessToken)
                        val userAccountInfoResponse = userRestApi.getMyUserInfo()

                        if (userAccountInfoResponse.isSuccessful) {
                            userAccountInfoResponse.body()?.let {
                                prefsDataStore.updateUserId(it.id)
                                _navigateToHome.emit(Unit)
                            }
                        } else {
                            Log.e(TAG, "токен получен, информация об аккаунте нет")
                        }
                    } else {
                        _error.emit(response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    Log.d(TAG, e.stackTraceToString())

                    _error.emit("Не удалось войти")
                }

                _loading.update { false }
            }
        }
    }

    fun onSignInClick() = viewModelScope.launch {
        _link.emit("http://79.174.91.149/?redirect_url=tinkoffer://sign-in")
    }

    companion object {
        private val TAG = SignInViewModel::class.simpleName
    }
}