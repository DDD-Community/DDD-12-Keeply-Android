package com.keeply.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.keeply.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), ContainerHost<SplashState, SplashSideEffect> {
    
    override val container: Container<SplashState, SplashSideEffect> = container(SplashState())
    
    init {
        checkUserLoginStatus()
    }
    
    private fun checkUserLoginStatus() = intent {
        reduce { state.copy(isLoading = true) }
        
        val accessToken = userRepository.fetchAccessToken()
        println("SplashViewModel - Access Token: $accessToken") // 디버깅용
        val isLoggedIn = !accessToken.isNullOrEmpty()
        println("SplashViewModel - Is Logged In: $isLoggedIn") // 디버깅용
        
        reduce { state.copy(isUserLoggedIn = isLoggedIn) }
        
        delay(2000)
        
        if (isLoggedIn) {
            postSideEffect(SplashSideEffect.NavigateToHome)
        } else {
            postSideEffect(SplashSideEffect.NavigateToOnboarding)
        }
        
        reduce { state.copy(isLoading = false) }
    }
}