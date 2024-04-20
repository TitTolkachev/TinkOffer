package ru.tinkoff.tinkoffer.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefsDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    val dataFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[KEY] ?: 11
    }

    suspend fun updateValue(value: Int) {
        context.dataStore.edit { settings ->
            settings[KEY] = value
        }
    }

    suspend fun updateTokens(newAccessToken: String? = null) {
        context.dataStore.edit { prefs ->
            newAccessToken?.let {
                prefs[ACCESS_TOKEN] = newAccessToken
            }
        }
    }

    suspend fun updateUserId(newId: String? = null) {
        context.dataStore.edit { prefs ->
            newId?.let {
                prefs[USER_ID] = newId
            }
        }
    }

    val tokenFlow = context.dataStore.data.map { prefs ->
        prefs[ACCESS_TOKEN] ?: ""
    }

    val userIdFlow = context.dataStore.data.map { prefs ->
        prefs[USER_ID] ?: ""
    }

    companion object {
        private val KEY = intPreferencesKey("key")

        private val ACCESS_TOKEN = stringPreferencesKey("access_token")

        private val USER_ID = stringPreferencesKey("user_id")

    }
}