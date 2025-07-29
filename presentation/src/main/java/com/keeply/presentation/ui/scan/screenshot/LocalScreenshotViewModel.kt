package com.keeply.presentation.ui.scan.screenshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keeply.domain.usecase.screenshot.GetLocalScreenshotsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalScreenshotViewModel @Inject constructor(
    getLocalScreenshotsUseCase: GetLocalScreenshotsUseCase
) : ViewModel() {

    val screenshots = getLocalScreenshotsUseCase()
        .flow
        .cachedIn(viewModelScope)
}