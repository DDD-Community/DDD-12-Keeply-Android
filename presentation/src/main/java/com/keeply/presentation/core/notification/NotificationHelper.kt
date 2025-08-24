package com.keeply.presentation.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.keeply.presentation.R
import com.keeply.presentation.ui.main.MainActivity
import android.Manifest
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

object NotificationHelper {
    
    private const val TAG = "NotificationHelper"
    const val CHANNEL_ID = "keeply_notification_channel"
    const val CHANNEL_NAME = "Keeply 알림"
    const val CHANNEL_DESCRIPTION = "Keeply 앱에서 보내는 알림입니다"
    
    fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }
    
    fun createNotificationChannel(context: Context) {
        Log.d(TAG, "알림 채널 생성 시작")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "알림 채널 생성 완료: $CHANNEL_ID")
        }
    }
    
    fun showNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        Log.d(TAG, "알림 표시 요청: $title - $body")
        
        // 알림 권한 체크
        if (!hasNotificationPermission(context)) {
            Log.w(TAG, "알림 권한이 없습니다")
            return
        }
        
        createNotificationChannel(context)
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_keeply_splash_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
        
        try {
            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, builder.build())
                Log.d(TAG, "알림 표시 성공: ID=$notificationId")
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "알림 표시 실패: 권한 오류", e)
        } catch (e: Exception) {
            Log.e(TAG, "알림 표시 실패: 알 수 없는 오류", e)
        }
    }
}