package com.keeply.presentation.core.navigation

import com.keeply.presentation.ui.folder.FolderTabs
import kotlinx.serialization.Serializable

sealed interface Route

sealed interface HomeRoute: Route {
    @Serializable
    data object Home: HomeRoute

    @Serializable
    data class Folder(val selectedTab: FolderTabs = FolderTabs.Folder): HomeRoute

    @Serializable
    data object Scan: HomeRoute

    @Serializable
    data object Alarm: HomeRoute

    @Serializable
    data object My: HomeRoute
}

sealed interface OnboardingRoute: Route {
    @Serializable
    data object Onboarding: OnboardingRoute
}

sealed interface FolderRoute: Route {
    @Serializable
    data object AddFolder: FolderRoute
    
    @Serializable
    data class FolderDetail(
        val folderId: Long,
        val folderName: String,
        val folderColor: String
    ): FolderRoute

    companion object {
        const val FOLDER_CREATED = "folder_created"
        const val FOLDER_UPDATED = "folder_updated"
    }
}

sealed interface AlertRoute: Route {
    @Serializable
    data object AlertSetting: AlertRoute

}

sealed interface AuthRoute: Route {
    @Serializable
    data object AuthSetting: AuthRoute

}