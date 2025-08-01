package com.keeply.presentation.ui.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.model.User
import com.keeply.domain.model.LoginKakao
import com.keeply.domain.usecase.user.LoginKakaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    val loginKakaoUseCase: LoginKakaoUseCase
) : ContainerHost<OnboardingState, OnboardingSideEffect>, ViewModel() {
    override val container: Container<OnboardingState, OnboardingSideEffect> = container(OnboardingState())

    fun updateOnboardingPage(onboardingPage: OnboardingPage) = intent {
        reduce {
            state.copy(
                onboardingPage = OnboardingPage.nextPage(onboardingPage)
            )
        }
    }

    fun loginKakao(
        user: User,
        successCallback: () -> Unit
    ) = viewModelScope.launch {
        loginKakaoUseCase(
            LoginKakao(
                id = user.id,
                connected_at = user.connectedAt,
                kakao_account = LoginKakao.Account(
                    profile_needs_agreement = user.kakaoAccount?.profileNeedsAgreement,
                    profile_nickname_needs_agreement = user.kakaoAccount?.profileNicknameNeedsAgreement,
                    profile_image_needs_agreement = user.kakaoAccount?.profileImageNeedsAgreement,
                    email_needs_agreement = user.kakaoAccount?.emailNeedsAgreement,
                    profile = LoginKakao.Profile(
                        nickname = user.kakaoAccount?.profile?.nickname,
                        thumbnail_image_url = user.kakaoAccount?.profile?.thumbnailImageUrl,
                        profile_image_url = user.kakaoAccount?.profile?.profileImageUrl
                    ),
                    email = user.kakaoAccount?.email
                )
            )
        ).catch {
            it.stackTrace
            Log.e("ERROR", it.toString())
        }.collect {
            // 로그인 성공 후 권한 요청 다이얼로그 표시
            intent {
                reduce { state.copy(showPermissionDialog = true) }
            }
        }
    }
    
    fun dismissPermissionDialog() = intent {
        reduce { state.copy(showPermissionDialog = false) }
        postSideEffect(OnboardingSideEffect.NavigateToHome)
    }
    
    fun requestPermission() = intent {
        reduce { state.copy(showPermissionDialog = false) }
        postSideEffect(OnboardingSideEffect.RequestPermission)
    }
    
    fun onPermissionResult(granted: Boolean) = intent {
        reduce { state.copy(hasPermission = granted) }
        postSideEffect(OnboardingSideEffect.NavigateToHome)
    }
}