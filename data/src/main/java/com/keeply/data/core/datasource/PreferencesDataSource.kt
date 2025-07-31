package com.keeply.data.core.datasource

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesDataSource(private val context: Context) {
    companion object {
        private val DONT_SHOW_SCAN_ON_BOARDING = booleanPreferencesKey("dont_show_scan_on_boarding")
    }

    val dontShowScanOnBoarding: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[DONT_SHOW_SCAN_ON_BOARDING] ?: false }

    suspend fun setDontShowDialog(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DONT_SHOW_SCAN_ON_BOARDING] = value
        }
    }
}