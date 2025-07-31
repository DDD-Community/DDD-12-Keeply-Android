package com.keeply.presentation.ui.folder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.keeply.presentation.ui.scan.navigation.ScanRoute

class FolderNavigator(
    val navController: NavHostController
) {
    val startDestination = FolderRoute.Folder
}

@Composable
fun rememberFolderNavigator(
    navController: NavHostController = rememberNavController()
): FolderNavigator = remember(navController) {
    FolderNavigator(navController)
}