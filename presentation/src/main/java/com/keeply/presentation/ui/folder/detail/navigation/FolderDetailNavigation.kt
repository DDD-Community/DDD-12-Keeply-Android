package com.keeply.presentation.ui.folder.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.FolderRoute
import com.keeply.presentation.ui.folder.detail.FolderDetailRoute
import com.keeply.presentation.ui.folder.screenshot.DetailScreenshotRoute

fun NavController.navigateFolderDetail() {
    navigate(FolderRoute.AddFolder)
}

fun NavGraphBuilder.folderDetailNavGraph(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateBackWithUpdate: () -> Unit
) {
    composable<FolderRoute.FolderDetail> { backStackEntry ->
        FolderDetailRoute(
            onBack = onNavigateBack,
            onBackWithUpdate = onNavigateBackWithUpdate,
            onClickImage = { imageId, folderName, folderColor ->
                navController.navigate(FolderRoute.DetailScreenshot(imageId, folderName, folderColor))
            }
        )
    }
    composable<FolderRoute.DetailScreenshot> {
        DetailScreenshotRoute(
            onBack = onNavigateBack
        )
    }
}
