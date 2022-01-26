/**
 * Created by naijun on 2022/01/25
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import com.skyline.msgbot.MainActivity
import com.skyline.msgbot.R

object ForegroundTask {
    private var wakeLock: PowerManager.WakeLock? = null

    fun startForeground(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        if (Build.VERSION.SDK_INT >= 26) context.startForegroundService(intent)
        else context.startService(intent)

        val noti = if (Build.VERSION.SDK_INT > 26) { //안드로이드 8.1 이상
            val nc = NotificationChannel("channel", "name", NotificationManager.IMPORTANCE_LOW)
            val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(nc)
            Notification.Builder(context, "channel")
        } else { //안드로이드 8.1 미만
            Notification.Builder(context)
        }

        noti.setSmallIcon(R.mipmap.ic_launcher);
        noti.setContentTitle("SkyLine");
        noti.setContentText("executing bot");
        val notiMgmt = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notiMgmt.notify(1, noti.build())

        wakeLock = (context.getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag").apply {
                acquire()
            }
        }
        wakeLock?.acquire()
    }

    fun release() {
        wakeLock?.release()
    }
}