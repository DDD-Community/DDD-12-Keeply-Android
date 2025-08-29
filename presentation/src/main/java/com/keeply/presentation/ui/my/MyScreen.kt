package com.keeply.presentation.ui.my

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import org.orbitmvi.orbit.compose.collectSideEffect
import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import com.keeply.presentation.core.components.KeeplyAlertCheckModal
import com.keeply.presentation.extend.restartApplication
import com.keeply.presentation.core.notification.NotificationHelper
import com.keeply.presentation.core.notification.FcmTokenManager
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.getSystemService
import coil.compose.rememberAsyncImagePainter
import com.keeply.domain.extend.default
import com.keeply.presentation.BuildConfig
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MyRoute(
    viewModel: MyViewModel = hiltViewModel(),
    navigateToAlertSetting: () -> Unit = {},
    navigateToAuthSetting: () -> Unit = {}
) {
    val context = LocalContext.current
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MySideEffect.SuccessLogout -> {
                (context as? Activity)?.restartApplication()
            }
            
            is MySideEffect.SuccessWithdraw -> {
                (context as? Activity)?.restartApplication()
            }

            is MySideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    MyScreen(
        context = context,
        scope = scope,
        userEmail = state.userEmail,
        userNickname = state.userNickname,
        userImage = state.userImage,
        onLogoutClick = viewModel::logout,
        onWithdrawClick = viewModel::showWithdrawModal,
        onAlertSettingClick = navigateToAlertSetting,
        onAuthSettingClick = navigateToAuthSetting
    )

    if (state.isShowWithdrawModal) {
        KeeplyAlertCheckModal(
            title = stringResource(R.string.my_page_withdrawal_dialog_title),
            content = stringResource(R.string.my_page_withdrawal_dialog_content),
            checkText = stringResource(R.string.my_page_withdrawal_dialog_check),
            confirmButtonText = stringResource(R.string.my_page_withdrawal_dialog_confirm),
            cancelButtonText = stringResource(R.string.my_page_withdrawal_dialog_cancel),
            confirmButtonCallback = {
                viewModel.dismissWithdrawModal()
                viewModel.withdraw()
            },
            onDismissCallback = {
                viewModel.dismissWithdrawModal()
            },
            cancelButtonCallback = {
                viewModel.dismissWithdrawModal()
            }
        )
    }
}

@Composable
fun MyScreen(
    context: Context,
    scope: CoroutineScope,
    userEmail: String? = null,
    userNickname: String? = null,
    userImage: String? = null,
    onLogoutClick: () -> Unit = {},
    onWithdrawClick: () -> Unit = {},
    onAlertSettingClick: () -> Unit = {},
    onAuthSettingClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = 112.dp
            )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KeeplyText(
                text = stringResource(R.string.my_page_title),
                style = KeeplyTheme.typography.header03
            )

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(KeeplyTheme.colors.neutralWhite)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(44.dp),
                    painter = userImage?.let { rememberAsyncImagePainter(it) } ?: ColorPainter(KeeplyTheme.colors.neutral200),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    KeeplyText(
                        text = userNickname.default(),
                        style = KeeplyTheme.typography.button01Suit
                    )

                    KeeplyText(
                        text = userEmail.default(),
                        style = KeeplyTheme.typography.button02Altform,
                        color = KeeplyTheme.colors.neutral800
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(KeeplyTheme.colors.neutralWhite)
                    .padding(16.dp)
            ) {
                KeeplyText(
                    text = stringResource(R.string.my_page_user_settings),
                    style = KeeplyTheme.typography.subtitle03,
                    color = KeeplyTheme.colors.neutral700
                )

                MyPageMenuItem(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = stringResource(R.string.my_page_notification_settings),
                    onClick = onAlertSettingClick
                )

                MyPageMenuItem(
                    text = stringResource(R.string.my_page_permission_settings),
                    onClick = onAuthSettingClick
                )

                //TODO 테스트를 위한 UI 추후 지워야함
                if (BuildConfig.DEBUG) {
                    MyPageMenuItem(
                        text = "FCM 테스트",
                        onClick = {
                            if (NotificationHelper.hasNotificationPermission(context)) {
                                NotificationHelper.showNotification(
                                    context = context,
                                    title = "FCM 테스트",
                                    body = "알림이 정상적으로 작동합니다! 🎉"
                                )
                            } else {
                                Toast.makeText(context, "알림 권한이 필요합니다. 설정에서 권한을 허용해주세요.", Toast.LENGTH_LONG).show()
                            }
                        }
                    )

                    MyPageMenuItem(
                        text = "FCM 토큰 확인",
                        onClick = {
                            val fcmTokenManager = FcmTokenManager()
                            scope.launch {
                                val token = fcmTokenManager.getCurrentToken()
                                if (token != null) {
                                    val clipboard = context.getSystemService<ClipboardManager>()
                                    val clip = ClipData.newPlainText("FCM Token", token)
                                    clipboard?.setPrimaryClip(clip)
                                    Toast.makeText(context, "FCM 토큰이 클립보드에 복사되었습니다", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "FCM 토큰을 가져올 수 없습니다", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(KeeplyTheme.colors.neutralWhite)
                    .padding(16.dp)
            ) {
                KeeplyText(
                    text = stringResource(R.string.my_page_customer_support),
                    style = KeeplyTheme.typography.subtitle03,
                    color = KeeplyTheme.colors.neutral700
                )

                MyPageMenuItem(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = stringResource(R.string.my_page_inquiry)
                )

                MyPageMenuItem(
                    text = stringResource(R.string.my_page_terms_policies)
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(KeeplyTheme.colors.neutralWhite)
                    .padding(16.dp)
            ) {
                KeeplyText(
                    text = stringResource(R.string.my_page_other),
                    style = KeeplyTheme.typography.subtitle03,
                    color = KeeplyTheme.colors.neutral700
                )

                MyPageMenuItem(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = stringResource(R.string.my_page_logout),
                    iconVisibility = false,
                    onClick = onLogoutClick
                )

                MyPageMenuItem(
                    text = stringResource(R.string.my_page_withdrawal),
                    iconVisibility = false,
                    onClick = onWithdrawClick
                )
            }
        }
    }
}

@Composable
fun MyPageMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    iconVisibility: Boolean = true,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        KeeplyText(
            modifier = Modifier
                .weight(1f),
            text = text,
            style = KeeplyTheme.typography.caption01
        )

        if (iconVisibility) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = KeeplyTheme.icons.chevronRight,
                contentDescription = null,
                tint = KeeplyTheme.colors.neutral500
            )
        }
    }
}

@Preview
@Composable
fun MyRoutePreview() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    KeeplyTheme {
        MyScreen(context = context, scope = scope)
    }
}