package com.keeply.presentation.ui.folder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.domain.extend.default
import com.keeply.presentation.core.navigation.FolderRoute
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.core.navigation.HomeRoute.Companion.FOLDER_CREATED
import com.keeply.presentation.ui.folder.FolderRoute
import com.keeply.presentation.ui.folder.add.navigation.navigateAddFolder

fun NavController.navigateFolder(navOptions: NavOptions) {
    navigate(HomeRoute.Folder, navOptions)
}

fun NavGraphBuilder.folderNavGraph(
    parentNavController: NavHostController
) {
    composable<HomeRoute.Folder> {
        val folderCreated = parentNavController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(FOLDER_CREATED).default()
        FolderRoute(
            onNavigateToAddFolder = { parentNavController.navigateAddFolder() },
            onNavigateToFolderDetail = { folderId, folderName ->
                parentNavController.navigate(FolderRoute.FolderDetail(folderId, folderName))
            },
            shouldRefresh = folderCreated
        )
    }
}