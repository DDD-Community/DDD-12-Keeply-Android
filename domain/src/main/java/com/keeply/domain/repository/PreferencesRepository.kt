package com.keeply.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val dontShowDialog: Flow<Boolean>
    suspend fun setDontShowDialog(value: Boolean)
}