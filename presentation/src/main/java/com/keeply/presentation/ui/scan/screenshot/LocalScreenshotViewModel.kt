package com.keeply.presentation.ui.scan.screenshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keeply.domain.usecase.scan.GetScanOnBoardingVisibilityUseCase
import com.keeply.domain.usecase.screenshot.GetLocalScreenshotsUseCase
import com.keeply.presentation.ui.onboarding.OnboardingSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LocalScreenshotViewModel @Inject constructor(
    private val getLocalScreenshotsUseCase: GetLocalScreenshotsUseCase,
    getScanOnBoardingVisibilityUseCase: GetScanOnBoardingVisibilityUseCase,
) : ContainerHost<ScreenshotState, ScreenshotSideEffect>, ViewModel() {

    override val container: Container<ScreenshotState, ScreenshotSideEffect> =
        container(ScreenshotState())

    fun loadScreenshots() = intent {
        val flow = getLocalScreenshotsUseCase().flow.cachedIn(viewModelScope)
        reduce { state.copy(screenshotsFlow = flow) }
    }

    fun setRestrictService(isRestricted: Boolean) = intent {
        reduce {
            state.copy(
                isRestricted = isRestricted
            )
        }
    }

    fun requestPermission() = intent {
        postSideEffect(ScreenshotSideEffect.RequestPermission)
    }

    fun setPermissionDeniedDialog(isShow: Boolean) = intent {
        reduce { state.copy(showPermissionDeniedDialog = isShow) }
    }

    // 온보딩 모달 노출 여부
    val showOnBoardingModal = getScanOnBoardingVisibilityUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
}