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

    fun loginKakao(user: User) = viewModelScope.launch {
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
                ),
                fcmToken = "ewaIq5mtQ8Ol-vXB7YERZX:APA91bHQTm9ZH6XreUVy9x2leXApUmSugZfOV__ILIxDn1cztZqzoAlKhaaE1cOgI5xyThpGOcq23zZn22WS9bhAWzTfeM5InEQ8LwOiNAFk_OpBoHMYTl8"
            )
        ).catch {
            it.stackTrace
            Log.e("ERROR", it.toString())
        }.collect {
            Log.e("TEST", it.toString())
        }
    }
}