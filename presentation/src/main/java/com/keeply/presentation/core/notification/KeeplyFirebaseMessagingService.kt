package com.keeply.presentation.core.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class KeeplyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {

        Log.d("onNewToken", token)
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //메세지를 전달 받는 function
        super.onMessageReceived(remoteMessage)
    }

}