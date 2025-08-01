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

fun NavGraphBuilder.addFolderNavGraph() {
    composable<FolderRoute.AddFolder> {
        AddFolderRoute()
    }
}