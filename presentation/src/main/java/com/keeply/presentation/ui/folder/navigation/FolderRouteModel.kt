package com.keeply.presentation.ui.folder.navigation

import kotlinx.serialization.Serializable

sealed interface FolderRouteModel

sealed interface FolderRoute: FolderRouteModel {
    @Serializable
    data object Folder : FolderRouteModel

    @Serializable
    data object AddFolder : FolderRouteModel
}