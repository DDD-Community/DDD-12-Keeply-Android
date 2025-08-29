package com.keeply.presentation.ui.my.setting.auth

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.my.setting.component.SettingToggleItem
import com.keeply.presentation.util.openAppSettings
import com.keeply.presentation.util.getRequiredImagePermission
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AuthSettingRoute(
    viewModel: AuthSettingViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updateStoragePermission(isGranted)
    }
    
    fun checkPermissions() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(
            context, 
            getRequiredImagePermission()
        ) == PermissionChecker.PERMISSION_GRANTED
        viewModel.updateStoragePermission(hasStoragePermission)
    }
    
    LaunchedEffect(Unit) {
        checkPermissions()
    }
    
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                checkPermissions()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AuthSettingSideEffect.RequestStoragePermission -> {
                storagePermissionLauncher.launch(getRequiredImagePermission())
            }
            is AuthSettingSideEffect.OpenAppSettings -> {
                openAppSettings(context)
            }
        }
    }
    
    AuthSettingScreen(
        state = state,
        onStoragePermissionToggle = { viewModel.handleStoragePermissionToggle() },
        onBackClick = onBackClick
    )
}

@Composable
fun AuthSettingScreen(
    state: AuthSettingState = AuthSettingState(),
    onStoragePermissionToggle: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            title = "권한 설정",
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
                text = "스크린샷 접근",
                isChecked = state.hasStoragePermission,
                onToggleValueChange = { _ -> onStoragePermissionToggle() }
            )
        }
    }
}

@Preview
@Composable
fun AuthSettingScreenPreview() {
    KeeplyTheme {
        AuthSettingScreen()
    }
}