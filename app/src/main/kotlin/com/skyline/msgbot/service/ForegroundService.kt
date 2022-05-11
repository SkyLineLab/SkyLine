/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.skyline.msgbot.service

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

object ForegroundService {
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

        noti.setTicker("executing service")
            .setContentText("SkyLine")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(Notification.PRIORITY_LOW)
            .setWhen(0)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
        val notiMgmt = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notiMgmt.notify(1, noti.build())

        wakeLock = (context.getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag").apply {
                acquire(10*60*1000L /*10 minutes*/)
            }
        }
        wakeLock?.acquire(10*60*1000L /*10 minutes*/)
    }

    fun release() {
        wakeLock?.release()
    }
}