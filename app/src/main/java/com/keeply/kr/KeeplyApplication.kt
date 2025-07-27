package com.keeply.kr

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KeeplyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        initFcmToken()
    }

    private fun initFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("test", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            Log.e("TEST", token)
        })
    }
}