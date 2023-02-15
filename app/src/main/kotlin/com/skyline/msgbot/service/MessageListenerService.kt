package com.skyline.msgbot.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.orhanobut.logger.Logger
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.model.ActionModel
import com.skyline.msgbot.reflow.Bot
import com.skyline.msgbot.reflow.action.ActionType
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
        if (
            sbn == null ||
            CoreHelper.allowPackageNames.find {
                sbn.packageName.startsWith(it)
            }.isNullOrEmpty()
        ) return

        if (sbn.notification == null) return
        if (sbn.notification.actions == null) return

        var replyActionModel: ActionModel? = null
        var readActionModel: ActionModel? = null

        sbn.notification.actions.forEach {
            if (
                it.title.toString().lowercase(Locale.getDefault()).contains("reply|답장".toRegex())
            ) {
                replyActionModel = ActionModel(
                    ActionType.REPLY,
                    it
                )
            } else if (
                it.title.toString().lowercase(Locale.getDefault()).contains("read|읽음".toRegex())
            ) {
                readActionModel = ActionModel(
                    ActionType.READ,
                    it
                )
            }
        }

        if (replyActionModel == null || readActionModel == null) return // 읽지 않은 메세지 알림

        if (sbn.notification.extras == null) throw RuntimeException("bundle not found")

        Logger.d(sbn.notification.extras)

        Bot.callMessageEvent(
            sbn,
            sbn.notification.extras,
            replyActionModel!!,
            readActionModel!!,
        )
    }

}