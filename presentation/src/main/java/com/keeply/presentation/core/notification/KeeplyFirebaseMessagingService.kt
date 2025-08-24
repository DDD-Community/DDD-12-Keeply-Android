package com.keeply.presentation.core.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeeplyFirebaseMessagingService : FirebaseMessagingService() {
    
    companion object {
        private const val TAG = "FCMService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "새로운 FCM 토큰: $token")
        
        // 서버에 토큰 전송 로직을 여기에 추가
        sendTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "메시지 수신: ${remoteMessage.from}")

        // 알림 데이터 처리
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "메시지 데이터: ${remoteMessage.data}")
            handleDataMessage(remoteMessage.data)
        }

        // 알림 표시
        remoteMessage.notification?.let { notification ->
            val title = notification.title ?: "Keeply"
            val body = notification.body ?: ""
            
            Log.d(TAG, "알림 표시: $title - $body")
            NotificationHelper.showNotification(this, title, body)
        }
    }

    private fun sendTokenToServer(token: String) {
        // TODO: 서버에 FCM 토큰을 전송하는 로직 구현
        // 예: API 호출을 통해 서버에 토큰 저장
        Log.d(TAG, "서버에 토큰 전송: $token")
    }
    
    private fun handleDataMessage(data: Map<String, String>) {
        // 백그라운드에서 받은 데이터 메시지 처리
        when (data["type"]) {
            "general" -> {
                val title = data["title"] ?: "Keeply"
                val message = data["message"] ?: ""
                NotificationHelper.showNotification(this, title, message)
            }
            // 필요에 따라 다른 타입들도 추가
        }
    }
}