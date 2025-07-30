package com.keeply.presentation.ui.scan.navigation

import kotlinx.serialization.Serializable

sealed interface ScanRouteModel

sealed interface ScanRoute: ScanRouteModel {
    @Serializable
    data object ScanScreenShot : ScanRouteModel

    @Serializable
    data class ScanBefore(
        val url: String
    ) : ScanRouteModel
}