package com.keeply.presentation.extend

import android.content.Context
import android.content.pm.PackageManager
import com.keeply.domain.extend.default

fun Context.getAppVersion(): String {
    return try {
        packageManager.getPackageInfo(packageName, 0).versionName.default()
    } catch (e: PackageManager.NameNotFoundException) {
        ""
    }
}