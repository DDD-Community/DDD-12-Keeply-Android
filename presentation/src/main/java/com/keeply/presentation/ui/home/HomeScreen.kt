package com.keeply.presentation.ui.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.keeply.presentation.core.components.InsightTextField
import com.keeply.presentation.core.components.KeeplyAlertModal
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyTab
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.KeeplyTextField
import com.keeply.presentation.core.components.Tag
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    HomeScreen(
        tabs = uiState.tabs,
        selectedTabIndex = uiState.selectedTabIndex,
        textField = uiState.textField,
        textFieldMaxLength = uiState.textFieldMaxLength,
        isTagChecked = uiState.isTagChecked,
        onCheckedChange = viewModel::tagCheckedChange,
        onClickTab = viewModel::onClickTab,
        onValueChange = viewModel::onValueChange
    )
}

@Composable
fun HomeScreen(
    tabs: ImmutableList<String>,
    selectedTabIndex: Int,
    textField: String,
    textFieldMaxLength: Int,
    isTagChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClickTab: (Int) -> Unit,
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current

    var isShowModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KeeplyTab(
            selectedTabIndex = selectedTabIndex,
            tabs = tabs,
            onClick = onClickTab
        )

        KeeplyText(
            text = tabs[selectedTabIndex],
            style = KeeplyTheme.typography.header01,
            color = KeeplyTheme.colors.success
        )

        InsightTextField(
            value = textField,
            placeholder = "인사이트를 적어주세요.",
            onValueChange = onValueChange,
            maxLength = textFieldMaxLength
        )

        Icon(
            painter = KeeplyTheme.icons.checkmark,
            contentDescription = "체크아이콘",
            tint = KeeplyTheme.colors.orange1000
        )

        Tag(
            label = "Label",
            checked = isTagChecked,
            onCheckedChange = onCheckedChange
        )

        KeeplyButton(
            onClick = { },
            text = "Button",
            icon = KeeplyTheme.icons.add,
            buttonSize = KeeplyButtonSize.SMALL_FIXED_WIDTH
        )

        KeeplyButton(
            onClick = { },
            text = "Button",
            textStyle = KeeplyTheme.typography.button01Suit,
            buttonStyle = KeeplyButtonStyle.SECONDARY,
            buttonSize = KeeplyButtonSize.XSMALL
        )

        KeeplyTextField(
            value = textField,
            onValueChange = onValueChange,
            placeholder = "placeHolder",
            helpIcon = KeeplyTheme.icons.error,
            helpText = "에러메세지 입니다.",
            showClearButton = true
        )

        Button(
            onClick = {
                loginWithKakaoTalk(context = context)
            }
        ) {
            KeeplyText(
                text = "카카오 로그인",
                style = KeeplyTheme.typography.header01
            )
        }

        KeeplyButton(
            text = "모달~ 열려라~",
            onClick = {
                isShowModal = true
            }
        )
    }

    if (isShowModal) {
        KeeplyAlertModal(
            title = "Keeply",
            content = "가즈아아아아아",
            confirmButtonText = "확인",
            cancelButtonText = "취소",
            confirmButtonCallback = {
                isShowModal = false
            },
            cancelButtonCallback = {
                isShowModal = false
            },
            onDismissCallback = {
                isShowModal = false
            }
        )
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
fun HomeScreenPreview() {
    KeeplyTheme {
        HomeScreen(
            tabs = persistentListOf("Keeply", "안칠수"),
            selectedTabIndex = 0,
            textField = "",
            textFieldMaxLength = 300,
            isTagChecked = false,
            onCheckedChange = {},
            onClickTab = {},
            onValueChange = {}
        )
    }
}
