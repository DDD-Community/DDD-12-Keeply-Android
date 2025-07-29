package com.keeply.presentation.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keeply.domain.usecase.scan.GetLocalScreenshotsUseCase
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCase
import com.keeply.domain.usecase.scan.SetDoNotShowDialogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    getLocalScreenshotsUseCase: GetLocalScreenshotsUseCase,
    getScanOnBoardingVisibilityUseCase: GetScanOnBoardingVisibilityUseCase,
    private val setDoNotShowDialogUseCase: SetDoNotShowDialogUseCase
) : ViewModel() {

    // 스크린샷 불러오기
    val screenshots = getLocalScreenshotsUseCase()
        .flow
        .cachedIn(viewModelScope)

    // 온보딩 모달 노출 여부
    val showOnBoardingModal = getScanOnBoardingVisibilityUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun onDoNotShowAgain() {
        viewModelScope.launch {
            setDoNotShowDialogUseCase(true)
        }
    }

}