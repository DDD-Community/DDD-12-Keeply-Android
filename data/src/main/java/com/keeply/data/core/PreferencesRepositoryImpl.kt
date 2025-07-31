package com.keeply.data.core

import com.keeply.data.core.datasource.PreferencesDataSource
import com.keeply.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val dataSource: PreferencesDataSource
) : PreferencesRepository {

    override val dontShowDialog: Flow<Boolean> = dataSource.dontShowScanOnBoarding

    override suspend fun setDontShowDialog(value: Boolean) {
        dataSource.setDontShowDialog(value)
    }
}