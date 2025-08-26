package com.keeply.presentation.core.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenManager @Inject constructor() {
    
    suspend fun getCurrentToken(): String? {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            token
        } catch (e: Exception) {
            null
        }
    }
    
    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                }
            }
    }
    
    fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                }
            }
    }
}