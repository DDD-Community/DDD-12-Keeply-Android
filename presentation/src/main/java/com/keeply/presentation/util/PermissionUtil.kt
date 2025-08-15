package com.keeply.presentation.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.keeply.presentation.ui.permission.ImagePermissionStatus

private fun hasPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context, permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

fun getRequiredImagePermission(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
}

fun checkImagePermissions(context: Context): ImagePermissionStatus {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        when {
            hasPermission(context, Manifest.permission.READ_MEDIA_IMAGES) ->
                ImagePermissionStatus.FULL_ACCESS
            hasPermission(context, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) ->
                ImagePermissionStatus.PARTIAL_ACCESS
            else -> ImagePermissionStatus.NO_ACCESS
        }
    } else {
        if (hasPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ImagePermissionStatus.FULL_ACCESS
        } else {
            ImagePermissionStatus.NO_ACCESS
        }
    }
}