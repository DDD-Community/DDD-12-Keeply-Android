package com.keeply.presentation.ui.naviagion

import kotlinx.serialization.Serializable

sealed interface Route

sealed interface HomeRoute: Route {
    @Serializable
    data object Main: Route
}