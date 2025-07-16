package com.keeply.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route

sealed interface HomeRoute: Route {
    @Serializable
    data object Main: Route
}