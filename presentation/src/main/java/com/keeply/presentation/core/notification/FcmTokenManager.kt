package com.keeply.presentation.core.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenManager @Inject constructor() {
    
    companion object {
        private const val TAG = "FcmTokenManager"
    }
    
    suspend fun getCurrentToken(): String? {
        return try {
            Log.d(TAG, "FCM 토큰 요청 시작")
            val token = FirebaseMessaging.getInstance().token.await()
            Log.d(TAG, "FCM 토큰 성공: ${token?.substring(0, 20)}...")
            Log.d(TAG, "전체 FCM 토큰: $token")
            token
        } catch (e: Exception) {
            Log.e(TAG, "FCM 토큰 가져오기 실패", e)
            null
        }
    }
    
    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "토픽 구독 성공: $topic")
                } else {
                    Log.e(TAG, "토픽 구독 실패: $topic", task.exception)
                }
            }
    }
    
    fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "토픽 구독 해제 성공: $topic")
                } else {
                    Log.e(TAG, "토픽 구독 해제 실패: $topic", task.exception)
                }
            }
    }
}