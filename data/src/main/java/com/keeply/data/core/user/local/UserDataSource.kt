package com.keeply.data.core.user.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.keeply.data.core.user.local.UserDataSource.PreferencesKey.ACCESS_TOKEN
import com.keeply.data.core.user.local.UserDataSource.PreferencesKey.FCM_TOKEN
import com.keeply.data.core.user.local.UserDataSource.PreferencesKey.REFRESH_TOKEN
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userDataStore: DataStore<Preferences>
) {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("AccessToken")
        val REFRESH_TOKEN = stringPreferencesKey("RefreshToken")
        val FCM_TOKEN = stringPreferencesKey("FcmToken")
    }

    suspend fun saveAccessToken(accessToken: String) {
        userDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun fetchAccessToken(): String? {
        val flow = userDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { preferences ->
                preferences[ACCESS_TOKEN]
            }
        return flow.firstOrNull()
    }

    suspend fun saveRefreshToken(accessToken: String) {
        userDataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = accessToken
        }
    }

    suspend fun fetchRefreshToken(): String? {
        val flow = userDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { preferences ->
                preferences[REFRESH_TOKEN]
            }
        return flow.firstOrNull()
    }

    suspend fun refreshFcmToken(token: String) {
        userDataStore.edit { preferences ->
            preferences[FCM_TOKEN] = token
        }
    }

    suspend fun fetchFcmToken(): String? {
        val flow = userDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { preferences ->
                preferences[FCM_TOKEN]
            }
        return flow.firstOrNull()
    }
}