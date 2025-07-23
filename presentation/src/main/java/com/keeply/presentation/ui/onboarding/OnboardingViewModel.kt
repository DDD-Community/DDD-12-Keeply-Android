package com.keeply.presentation.ui.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ContainerHost<OnboardingState, OnboardingSideEffect>, ViewModel() {
    override val container: Container<OnboardingState, OnboardingSideEffect> = container(OnboardingState())

    fun updateOnboardingPage(onboardingPage: OnboardingPage) = intent {
        reduce {
            state.copy(
                onboardingPage = OnboardingPage.nextPage(onboardingPage)
            )
        }
    }
}