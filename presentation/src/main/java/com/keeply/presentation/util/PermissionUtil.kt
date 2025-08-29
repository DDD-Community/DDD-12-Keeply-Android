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

fun isPermissionGranted(context: Context): Boolean
    = hasPermission(context, getRequiredImagePermission())

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

fun getPhotoPermissionStatus(context: Context): ImagePermissionStatus {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        // API 34 이상 → 부분 접근 권한까지 체크
        val hasFullAccess = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        // 제한적 허용
        val hasPartialAccess = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
        ) == PackageManager.PERMISSION_GRANTED

        when {
            hasFullAccess -> ImagePermissionStatus.FULL_ACCESS
            hasPartialAccess -> ImagePermissionStatus.PARTIAL_ACCESS
            else -> ImagePermissionStatus.NO_ACCESS
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // API 33 → 모든 사진 접근만 있음
        val hasFullAccess = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFullAccess) ImagePermissionStatus.FULL_ACCESS else ImagePermissionStatus.NO_ACCESS
    } else {
        // API 32 이하 → READ_EXTERNAL_STORAGE 사용
        val hasLegacyAccess = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (hasLegacyAccess) ImagePermissionStatus.FULL_ACCESS else ImagePermissionStatus.NO_ACCESS
    }
}
