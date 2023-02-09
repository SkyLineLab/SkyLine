package com.skyline.msgbot.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.skyline.msgbot.R
import com.skyline.msgbot.view.MainActivity

class ForegroundService : Service() {

    private val channelId = "ForegroundServiceChannel"

    var counter = 0

    private fun getForegroundNotification(intent: PendingIntent): Notification {
        return if (Build.VERSION.SDK_INT >= 30) {
            val builder = Notification.Builder(this, channelId)
                .setContentTitle("Foreground Service")
                .setContentText("sssss")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(Notification.PRIORITY_LOW)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
                .setVibrate(null) // Passing null here silently fails
                .setContentIntent(intent)
                .setFlag(Notification.FLAG_FOREGROUND_SERVICE, true)

            if (Build.VERSION.SDK_INT >= 31) {
                builder.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE)
            }

            return builder.build()
        } else {
            NotificationCompat.Builder(this, channelId)
                .setContentTitle("Foreground Service")
                .setContentText("sssss")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(Notification.PRIORITY_LOW)
                .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
                .setVibrate(null) // Passing null here silently fails
                .setContentIntent(intent)
                .build()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = getForegroundNotification(pendingIntent)

        startForeground(1, notification)

        val notiMgmt = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notiMgmt.notify(1, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            serviceChannel.enableVibration(false)
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

}