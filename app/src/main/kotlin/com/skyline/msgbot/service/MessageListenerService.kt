package com.skyline.msgbot.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import java.util.*

class MessageListenerService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        Logger.d("Notification Listener Service Create")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("Notification Listener Service Destroy")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        Logger.d("onNotificationPosted")
        if (
            sbn == null ||
            CoreHelper.allowPackageNames.find {
                sbn.packageName.startsWith(it)
            }.isNullOrEmpty()
        ) return

        if (sbn.notification == null) return
        if (sbn.notification.actions == null) return

        sbn.notification.actions.forEach {
            if (
                it.title.toString().lowercase(Locale.getDefault()).contains("reply") ||
                it.title.toString().lowercase(Locale.getDefault()).contains("답장")
            ) {
                val data = sbn.notification.extras

                Logger.d(data)

                if (data == null) {
                    Logger.d("data is null")
                    return
                }
                Logger.d("actionSize = ${sbn.notification.actions.size}")
            }
        }
    }

}