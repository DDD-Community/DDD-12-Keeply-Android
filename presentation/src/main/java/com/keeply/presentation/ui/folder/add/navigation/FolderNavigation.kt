package com.keeply.presentation.ui.folder.add.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.FolderRoute
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.folder.FolderRoute
import com.keeply.presentation.ui.folder.add.AddFolderRoute

fun NavController.navigateAddFolder() {
    navigate(FolderRoute.AddFolder)
}

fun NavGraphBuilder.addFolderNavGraph(
    navController: NavController,
    onNavigateBack: () -> Unit
) {
    composable<FolderRoute.AddFolder> {
        AddFolderRoute(
            onNavigateBack = onNavigateBack,
            onNavigateSaveBack = {
                // 이전 화면에 결과 전달
                navController.previousBackStackEntry?.savedStateHandle?.set("folder_created", true)
                onNavigateBack()
            }
        )
    }
}