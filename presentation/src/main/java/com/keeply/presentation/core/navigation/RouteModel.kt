package com.keeply.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route

sealed interface HomeRoute: Route {
    @Serializable
    data object Home: HomeRoute

    @Serializable
    data object Folder: HomeRoute

    @Serializable
    data object Scan: HomeRoute

    @Serializable
    data object Alarm: HomeRoute

    @Serializable
    data object My: HomeRoute

    companion object {
        const val FOLDER_CREATED = "folder_created"
    }
}

sealed interface OnboardingRoute: Route {
    @Serializable
    data object Onboarding: OnboardingRoute
}

sealed interface FolderRoute: Route {
    @Serializable
    data object AddFolder: FolderRoute
}