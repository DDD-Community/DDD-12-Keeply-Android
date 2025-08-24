package com.keeply.data.user.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.keeply.data.user.local.UserDataSource.PreferencesKey.ACCESS_TOKEN
import com.keeply.data.user.local.UserDataSource.PreferencesKey.FCM_TOKEN
import com.keeply.data.user.local.UserDataSource.PreferencesKey.REFRESH_TOKEN
import com.keeply.data.user.local.UserDataSource.PreferencesKey.USER_EMAIL
import com.keeply.data.user.local.UserDataSource.PreferencesKey.USER_NICKNAME
import com.keeply.data.user.local.UserDataSource.PreferencesKey.USER_IMAGE
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
        val USER_EMAIL = stringPreferencesKey("UserEmail")
        val USER_NICKNAME = stringPreferencesKey("UserNickname")
        val USER_IMAGE = stringPreferencesKey("UserImage")
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

    suspend fun saveRefreshToken(refreshToken: String) {
        userDataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
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
    
    suspend fun clearAccessToken() {
        userDataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }

    suspend fun clearRefreshToken() {
        userDataStore.edit { preferences ->
            preferences.remove(REFRESH_TOKEN)
        }
    }

    suspend fun saveUserEmail(email: String) {
        userDataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }

    suspend fun getUserEmail(): String? {
        val flow = userDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { preferences ->
                preferences[USER_EMAIL]
            }
        return flow.firstOrNull()
    }

    suspend fun saveUserNickname(nickname: String) {
        userDataStore.edit { preferences ->
            preferences[USER_NICKNAME] = nickname
        }
    }

    suspend fun getUserNickname(): String? {
        val flow = userDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { preferences ->
                preferences[USER_NICKNAME]
            }
        return flow.firstOrNull()
    }

    suspend fun saveUserImage(image: String) {
        userDataStore.edit { preferences ->
            preferences[USER_IMAGE] = image
        }
    }

    suspend fun getUserImage(): String? {
        val flow = userDataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { preferences ->
                preferences[USER_IMAGE]
            }
        return flow.firstOrNull()
    }
}