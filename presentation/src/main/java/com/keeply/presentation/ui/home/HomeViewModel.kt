package com.keeply.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keeply.domain.extend.default
import com.keeply.domain.home.usecase.GetHomeDataUseCase
import com.keeply.domain.usecase.screenshot.GetLocalScreenshotsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val getLocalScreenshotsUseCase: GetLocalScreenshotsUseCase
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())

    val latestScreenshotCount = 10

    var screenshots = getLocalScreenshotsUseCase(pageSize = latestScreenshotCount)
        .flow
        .cachedIn(viewModelScope)

    fun loadHomeData() = intent {
        viewModelScope.launch {
            getHomeDataUseCase()
                .onStart { reduce { state.copy(isLoading = true) } }
                .onCompletion { state.copy(isLoading = false) }
                .catch { error ->
                    postSideEffect(HomeSideEffect.ShowError(error.message.default()))
                }.collect { homeData ->
                    reduce { state.copy(homeData = homeData) }
                }
        }
    }

    fun setRestrictService(isRestricted: Boolean) = intent {
        reduce { state.copy(isRestrictedService = isRestricted) }

        if (isRestricted.not()) {
            screenshots = getLocalScreenshotsUseCase(pageSize = latestScreenshotCount)
                .flow
                .cachedIn(viewModelScope)
        }
    }
}