package com.example.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyIntentService : IntentService(SERVICE_NAME) {

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, getNotification())
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        for (i in 0..100) {
            Thread.sleep(1000)
            log("Timer $i")
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }


    private fun log(message: String) {
        Log.i("MyIntentService", message)
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(): Notification {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("IntentService")
            .setContentText("Уведомление")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        return notification
    }

    companion object {
        private const val SERVICE_NAME = "intent_service"
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_NAME = "notification_channel_name"
        private const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
        fun newIntent(context: Context): Intent {
            return Intent(context, MyIntentService::class.java)
        }
    }
}

