package com.keeply.presentation.core.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.keeply.domain.extend.default
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeeplyFirebaseMessagingService : FirebaseMessagingService() {
    
    companion object {
        private const val DEFAULT_TITLE = "Keeply"
        private const val TITLE = "title"
        private const val BODY = "body"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // 알림 데이터 처리
        remoteMessage.data.isNotEmpty().let {
            handleDataMessage(remoteMessage.data)
        }

        // 알림 표시
        remoteMessage.notification?.let { notification ->
            val title = notification.title.default()
            val body = notification.body.default()
            
            NotificationHelper.showNotification(this, title, body)
        }
    }
    
    private fun handleDataMessage(data: Map<String, String>) {
        // 백그라운드에서 받은 데이터 메시지 처리
        val title = data[TITLE].default()
        val message = data[BODY].default()
        
        // title이나 body가 있으면 알림 표시
        if (title.isNotEmpty() || message.isNotEmpty()) {
            NotificationHelper.showNotification(this, title, message)
        }
    }
}