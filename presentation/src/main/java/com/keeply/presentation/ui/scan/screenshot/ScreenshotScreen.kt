package com.keeply.presentation.ui.scan.screenshot

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.keeply.domain.model.Screenshot
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral100
import com.keeply.presentation.ui.permission.ImagePermissionStatus
import com.keeply.presentation.ui.permission.PermissionDeniedDialog
import com.keeply.presentation.ui.permission.PermissionUpgradeDialog
import com.keeply.presentation.util.getPhotoPermissionStatus
import com.keeply.presentation.util.getRequiredImagePermission
import com.keeply.presentation.util.openAppSettings
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ScreenshotRoute(
    onBack: () -> Unit,
    onNavigateToScanBefore: (Uri, Boolean) -> Unit,
    viewModel: LocalScreenshotViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.collectAsState()
    val lazyPagingItems = uiState.screenshotsFlow.collectAsLazyPagingItems()
    val isShowOnBoarding by viewModel.showOnBoardingModal.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.setPermissionDeniedDialog(!isGranted)
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ScreenshotSideEffect.RequestPermission -> permissionLauncher.launch(
                getRequiredImagePermission()
            )
        }
    }

    ScreenshotScreen(
        lazyPagingItems = lazyPagingItems,
        isRestricted = uiState.isRestricted,
        showPermissionDeniedDialog = uiState.showPermissionDeniedDialog,
        onBack = onBack,
        setRestrictService = viewModel::setRestrictService,
        setPermissionDeniedDialog = viewModel::setPermissionDeniedDialog,
        onRequestPermission = { viewModel.requestPermission() },
        onClickSetting = { openAppSettings(context) },
        onLoadScreenshots = { viewModel.loadScreenshots() },
        onNavigateToDetail = { uri -> onNavigateToScanBefore(uri, isShowOnBoarding) },
    )
}

@Composable
fun ScreenshotScreen(
    lazyPagingItems: LazyPagingItems<Screenshot>,
    isRestricted: Boolean = false,
    showPermissionDeniedDialog: Boolean,
    onBack: () -> Unit,
    setRestrictService: (Boolean) -> Unit = {},
    setPermissionDeniedDialog: (Boolean) -> Unit = {},
    onRequestPermission: () -> Unit = {},
    onClickSetting: () -> Unit = {},
    onLoadScreenshots: () -> Unit = {},
    onNavigateToDetail: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenLifecycleOwner = LocalLifecycleOwner.current

    var showUpgradeDialog by remember { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // '제한된 액세스 허용'일 때만 제한된 서비스 표시
                val photoPermissionStatus = getPhotoPermissionStatus(context)
                when (photoPermissionStatus) {
                    ImagePermissionStatus.FULL_ACCESS -> {
                        setRestrictService(false)
                    }

                    ImagePermissionStatus.PARTIAL_ACCESS -> {
                        setRestrictService(true)
                    }

                    ImagePermissionStatus.NO_ACCESS -> {
                        setRestrictService(false)
                        showUpgradeDialog = true
                    }
                }

                // resume시 목록 갱신
                onLoadScreenshots()
            }
        }
        screenLifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            screenLifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ScreenshotFrame(
        count = lazyPagingItems.itemCount,
        isRestricted = isRestricted,
        onBack = onBack,
        onClickSetting = onClickSetting
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(lazyPagingItems.itemCount) { index ->
                val isFirstRow = index < 3

                lazyPagingItems[index]?.let { screenshot ->
                    ImageFrame(
                        painter = rememberAsyncImagePainter(screenshot.uri),
                        modifier = Modifier
                            .padding(top = if (isFirstRow) 28.dp else 0.dp)
                            .clickable { onNavigateToDetail(screenshot.uri) }
                    )
                }
            }
        }
    }

    if (showUpgradeDialog) {
        PermissionUpgradeDialog(
            onDismiss = {
                showUpgradeDialog = false
                setPermissionDeniedDialog(true)
            },
            onConfirm = {
                // 시스템 다이얼로그 노출
                onRequestPermission()
                showUpgradeDialog = false
            }
        )
    }

    if (showPermissionDeniedDialog) {
        PermissionDeniedDialog(
            onDismiss = {
                setPermissionDeniedDialog(false)
                onBack()
            },
            onConfirm = {
                // 설정 으로 이동
                openAppSettings(context)
                setPermissionDeniedDialog(false)
            }
        )
    }
}

@Composable
fun ScreenshotFrame(
    count: Int = 0,
    isRestricted: Boolean = false,
    onBack: () -> Unit,
    onClickSetting: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(neutral100)
                .weight(1f)
        ) {
            KeeplyAppBar(
                customTitleContent = {
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterStart),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        KeeplyText(
                            text = "Screenshots",
                            style = KeeplyTheme.typography.header03,
                        )

                        KeeplyText(
                            text = "$count",
                            style = KeeplyTheme.typography.header04,
                            color = KeeplyTheme.colors.neutral500
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                onClickLeading = onBack
            )

            content()
        }

        if (isRestricted) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .background(KeeplyTheme.colors.neutral200)
                    .clickable { onClickSetting() },
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KeeplyText(
                        text = "Keeply가 선택한 수의 사진에만 액세스하도록 허용합니다.",
                        style = KeeplyTheme.typography.caption02,
                        color = KeeplyTheme.colors.neutral800
                    )

                    KeeplyText(
                        text = "관리",
                        style = KeeplyTheme.typography.button01Suit,
                        color = KeeplyTheme.colors.neutral900
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScreenshotFrame(
            onBack = { },
            isRestricted = true,
            onClickSetting = { }
        ) {
            KeeplyText(
                text = "Contents",
                style = KeeplyTheme.typography.header03,
            )
        }
    }
}