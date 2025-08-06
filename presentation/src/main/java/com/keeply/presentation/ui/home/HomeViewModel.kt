package com.keeply.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keeply.domain.home.usecase.GetHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())
    
    init {
        loadHomeData()
    }
    
    fun loadHomeData() = intent {
        reduce { state.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            getHomeDataUseCase()
                .catch { error ->
                    reduce { 
                        state.copy(
                            isLoading = false, 
                            error = error.message
                        ) 
                    }
                    postSideEffect(HomeSideEffect.ShowError(error.message ?: "홈 데이터를 불러오는데 실패했습니다"))
                }
                .collectLatest { homeData ->
                    reduce { 
                        state.copy(
                            isLoading = false,
                            homeData = homeData,
                            error = null
                        ) 
                    }
                }
        }
    }
    
    fun retry() {
        loadHomeData()
    }
}