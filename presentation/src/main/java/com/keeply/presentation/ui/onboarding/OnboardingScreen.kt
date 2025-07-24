package com.keeply.presentation.ui.onboarding

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.onboarding.component.KakaoLoginButton
import com.keeply.presentation.ui.onboarding.component.OnboardingPager
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun OnboardingRoute(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    OnboardingScreen(
        onboardingPage = uiState.onboardingPage,
        updateOnboardingPage = viewModel::updateOnboardingPage
    )
}

@Composable
fun OnboardingScreen(
    onboardingPage: OnboardingPage,
    updateOnboardingPage: (OnboardingPage) -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        OnboardingPager(
            modifier = Modifier
                .padding(top = 40.dp)
                .width(246.dp)
                .align(Alignment.TopCenter),
            onboardingPage = onboardingPage
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            KeeplyTheme.colors.neutral100,
                            KeeplyTheme.colors.neutral100
                        )
                    )
                )
        )

        val bottomModifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 50.dp
            )
            .fillMaxWidth()
            .align(Alignment.BottomCenter)

        if (onboardingPage != OnboardingPage.THIRD) {
            KeeplyButton(
                modifier = bottomModifier,
                text = stringResource(R.string.next),
                onClick = { updateOnboardingPage(onboardingPage) }
            )
        } else {
            KakaoLoginButton(
                modifier = bottomModifier,
                onClick = {
                    loginWithKakaoTalk(context)
                }
            )
        }
    }
}

private fun loginWithKakaoTalk(
    context: Context,
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            error.printStackTrace()
            Log.e("user", "실패")
        } else if (token != null) {
            UserApiClient.instance.me { user, _ ->
                Log.e("token", token.toString())
                Log.e("user", user.toString())
            }
        }
    }
    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(
            context = context
        ) { token, error ->
            if (error != null) {
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    Log.e("user", "실패")
                    return@loginWithKakaoTalk
                }
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(
                    context = context,
                    callback = callback
                )
            } else if (token != null) {
                UserApiClient.instance.me { user, _ ->
                    Log.e("user", "카카오 앱 로그인 성공")
                }
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(
            context = context,
            callback = callback
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    KeeplyTheme {
        OnboardingScreen(
            onboardingPage = OnboardingPage.FIRST
        )
    }
}