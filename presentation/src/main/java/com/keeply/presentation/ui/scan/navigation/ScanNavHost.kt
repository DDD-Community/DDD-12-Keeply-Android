package com.keeply.presentation.ui.scan.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeply.presentation.ui.scan.scanafter.ScanAfterRoute
import com.keeply.presentation.ui.scan.scanbefore.ScanBeforeRoute
import com.keeply.presentation.ui.scan.crop.CropRoute
import com.keeply.presentation.ui.scan.screenshot.ScreenshotRoute

@Composable
fun ScanNavHost(
    navigator: ScanNavigator,
    onNavigateToHome: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {

        composable<ScanRoute.ScanScreenShot> {
            ScreenshotRoute(
                onBack = onNavigateBack,
                onNavigateToScanBefore = { uri, isShowOnBoarding ->
                    navigator.navController.navigate(ScanRoute.ScanBefore(Uri.encode(uri.toString()), isShowOnBoarding))
                }
            )
        }
        composable<ScanRoute.ScanBefore> { backStackEntry ->
            ScanBeforeRoute(
                onBack = { navigator.navController.popBackStack() },
                onNavigateToCrop = { uri ->
                    navigator.navController.navigate(ScanRoute.ScanCrop(Uri.encode(uri.toString())))
                },
                onNavigateToScanAfter = { uri, result, isSkip ->
                    navigator.navController.navigate(ScanRoute.ScanAfter(Uri.encode(uri.toString()), result.cachedImageId, result.detectedText, isSkip))
                },
                croppedImageUri = backStackEntry.savedStateHandle.get<String>("croppedImageUri")
            )
        }
        composable<ScanRoute.ScanCrop> { backStackEntry ->
            CropRoute(
                onBack = { navigator.navController.popBackStack() },
                onCropCompleted = { croppedImageUri ->
                    // 크롭 완료 후 이전 화면으로 돌아가면서 크롭된 이미지 URI 전달
                    navigator.navController.previousBackStackEntry?.savedStateHandle?.set("croppedImageUri", croppedImageUri)
                    navigator.navController.popBackStack()
                }
            )
        }
        composable<ScanRoute.ScanAfter> { backStackEntry ->
            ScanAfterRoute(
                onBack = { navigator.navController.popBackStack() },
                onNavigateToHome = onNavigateToHome // TODO: Folder이동으로 바뀔 듯
            )
        }
    }
}