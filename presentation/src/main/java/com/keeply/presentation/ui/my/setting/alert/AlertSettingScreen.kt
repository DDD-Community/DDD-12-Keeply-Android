package com.keeply.presentation.ui.my.setting.alert

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.my.setting.component.SettingToggleItem
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingAlertRoute(
    viewModel: SettingAlertViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current
    
    // 권한 요청 런처
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updateNotificationPermission(isGranted)
    }
    
    // 권한 상태 확인 및 업데이트
    LaunchedEffect(Unit) {
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
        viewModel.updateNotificationPermission(hasPermission)
    }
    
    // 화면이 다시 포커스될 때 권한 상태 재확인
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    NotificationManagerCompat.from(context).areNotificationsEnabled()
                } else {
                    NotificationManagerCompat.from(context).areNotificationsEnabled()
                }
                viewModel.updateNotificationPermission(hasPermission)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingAlertSideEffect.ShowError -> {
                // TODO: Show error message (예: SnackBar, Toast 등)
            }
            is SettingAlertSideEffect.RequestNotificationPermission -> {
                // 권한이 없을 때: 권한 요청 모달 표시
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    // Android 12 이하에서는 알림이 기본적으로 허용됨
                    viewModel.updateNotificationPermission(true)
                }
            }
            is SettingAlertSideEffect.OpenNotificationSettings -> {
                // 권한이 있을 때: 설정 화면으로 이동하여 사용자가 직접 끌 수 있도록
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        }
    }
    
    SettingAlertScreen(
        state = state,
        onStorageNotificationChange = { enabled -> viewModel.updateStorageNotification(enabled) },
        onMarketingNotificationChange = { enabled -> viewModel.updateMarketingNotification(enabled) },
        onNotificationPermissionToggle = { viewModel.handleNotificationPermissionToggle() },
        onBackClick = onBackClick
    )
}

@Composable
fun SettingAlertScreen(
    state: SettingAlertState = SettingAlertState(),
    onStorageNotificationChange: (Boolean) -> Unit = {},
    onMarketingNotificationChange: (Boolean) -> Unit = {},
    onNotificationPermissionToggle: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            title = "알림 설정",
            leadingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.chevronLeft,
                    contentDescription = "Back",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            onClickLeading = onBackClick,
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 11.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            SettingToggleItem(
                text = "저장 용량 알림",
                isChecked = state.allowStorageNotification,
                onToggleValueChange = onStorageNotificationChange
            )

            SettingToggleItem(
                text = "마케팅 알림",
                isChecked = state.allowMarketingNotification,
                onToggleValueChange = onMarketingNotificationChange
            )

            SettingToggleItem(
                text = "알림 권한",
                isChecked = state.hasNotificationPermission,
                onToggleValueChange = { _ -> onNotificationPermissionToggle() }
            )
        }
    }
}

@Preview
@Composable
fun SettingAlertScreenPreview() {
    KeeplyTheme {
        SettingAlertScreen()
    }
}