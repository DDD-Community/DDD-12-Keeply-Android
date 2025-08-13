package com.keeply.presentation.extend

import android.app.Activity
import android.content.Intent
import com.keeply.presentation.ui.main.MainActivity

fun Activity.restartApplication(startActivityClass: Class<out Activity> = MainActivity::class.java) {
    startActivity(Intent(this, startActivityClass))
    finishAffinity()
}