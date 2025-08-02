package com.keeply.presentation.ui.onboarding

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.onboarding.component.KakaoLoginButton
import com.keeply.presentation.ui.onboarding.component.OnboardingPager
import com.keeply.presentation.ui.onboarding.component.PermissionRequestDialog
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OnboardingRoute(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onEnterHome: () -> Unit
) {
    val uiState by viewModel.collectAsState()
    val context = LocalContext.current
    
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPermissionResult(isGranted)
    }
    
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is OnboardingSideEffect.NavigateToHome -> onEnterHome()
            is OnboardingSideEffect.RequestPermission -> {
                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
                permissionLauncher.launch(permission)
            }
        }
    }

    OnboardingScreen(
        uiState = uiState,
        updateOnboardingPage = viewModel::updateOnboardingPage,
        callLogin = { user ->
            viewModel.loginKakao(
                user = user,
                successCallback = onEnterHome
            )
        },
        onDismissPermissionDialog = viewModel::dismissPermissionDialog,
        onRequestPermission = viewModel::requestPermission
    )
}

@Composable
fun OnboardingScreen(
    uiState: OnboardingState,
    updateOnboardingPage: (OnboardingPage) -> Unit = {},
    callLogin: (User) -> Unit = {},
    onDismissPermissionDialog: () -> Unit = {},
    onRequestPermission: () -> Unit = {}
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
            onboardingPage = uiState.onboardingPage
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
            .height(50.dp)
            .align(Alignment.BottomCenter)

        if (uiState.onboardingPage != OnboardingPage.THIRD) {
            KeeplyButton(
                modifier = bottomModifier,
                text = stringResource(R.string.next),
                onClick = { updateOnboardingPage(uiState.onboardingPage) }
            )
        } else {
            KakaoLoginButton(
                modifier = bottomModifier,
                onClick = {
                    loginWithKakaoTalk(
                        context = context,
                        callLogin = callLogin
                    )
                }
            )
        }
    }
    
    // 권한 요청 다이얼로그
    if (uiState.showPermissionDialog) {
        PermissionRequestDialog(
            onDismiss = onDismissPermissionDialog,
            onConfirm = onRequestPermission
        )
    }
}

private fun loginWithKakaoTalk(
    context: Context,
    callLogin: (User) -> Unit,
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            error.printStackTrace()
            Log.e("KakaoLogin", "카카오 로그인 실패: ${error.message}")
        } else if (token != null) {
            Log.d("KakaoLogin", "카카오 로그인 성공, 토큰: ${token.accessToken}")
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e("KakaoLogin", "사용자 정보 요청 실패: ${error.message}")
                } else if (user != null) {
                    Log.d("KakaoLogin", "사용자 정보 요청 성공: ${user.id}")
                    callLogin(user)
                }
            }
        }
    }
    
    Log.d("KakaoLogin", "카카오톡 설치 여부: ${UserApiClient.instance.isKakaoTalkLoginAvailable(context)}")
    
    // 카카오톡이 설치되어 있으면 앱으로 로그인, 아니면 웹으로 로그인
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        Log.d("KakaoLogin", "카카오톡으로 로그인 시도")
        UserApiClient.instance.loginWithKakaoTalk(
            context = context
        ) { token, error ->
            if (error != null) {
                Log.e("KakaoLogin", "카카오톡 로그인 에러: ${error.message}")
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    Log.e("KakaoLogin", "사용자가 로그인 취소")
                    return@loginWithKakaoTalk
                }
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                Log.d("KakaoLogin", "카카오 계정으로 로그인 재시도")
                UserApiClient.instance.loginWithKakaoAccount(
                    context = context,
                    callback = callback
                )
            } else if (token != null) {
                Log.d("KakaoLogin", "카카오톡 로그인 성공")
                callback(token, null)
            }
        }
    } else {
        Log.d("KakaoLogin", "카카오 계정으로 로그인 시도")
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
            uiState = OnboardingState()
        )
    }
}