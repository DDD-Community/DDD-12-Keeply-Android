package com.keeply.presentation.ui.scan.screenshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCase
import com.keeply.domain.usecase.screenshot.GetLocalScreenshotsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocalScreenshotViewModel @Inject constructor(
    getLocalScreenshotsUseCase: GetLocalScreenshotsUseCase,
    getScanOnBoardingVisibilityUseCase: GetScanOnBoardingVisibilityUseCase,
) : ViewModel() {

    val screenshots = getLocalScreenshotsUseCase()
        .flow
        .cachedIn(viewModelScope)

    // 온보딩 모달 노출 여부
    val showOnBoardingModal = getScanOnBoardingVisibilityUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
}