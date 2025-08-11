package com.keeply.presentation.ui.scan.navigation

import com.keeply.domain.model.ScanAnalyze
import kotlinx.serialization.Serializable

sealed interface ScanRouteModel

sealed interface ScanRoute: ScanRouteModel {
    @Serializable
    data object ScanScreenShot : ScanRouteModel

    @Serializable
    data class ScanBefore(
        val url: String
    ) : ScanRouteModel

    @Serializable
    data class ScanCrop(
        val url: String
    ) : ScanRouteModel

    @Serializable
    data class ScanAfter(
        val url: String,
        val cachedImageId: String?,
        val detectedText: String?,
        val recommendedTags: List<String>?
    ) : ScanRouteModel
}