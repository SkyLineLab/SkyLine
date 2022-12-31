package com.skyline.msgbot.reflow.event.message

import android.os.Bundle
import android.service.notification.StatusBarNotification
import com.skyline.msgbot.core.CoreHelper
import com.skyline.msgbot.model.ActionModel
import com.skyline.msgbot.reflow.internal.ProfileImage
import com.skyline.msgbot.reflow.project.Project

object MessageEventUtil {

    fun createMessageEvent(sbn: StatusBarNotification, chat: Bundle, replyActionModel: ActionModel, readActionModel: ActionModel, project: Project): MessageEvent {
        val context = CoreHelper.contextGetter!!.invoke()

        val message = chat.get("android.text").toString()
        val profileImage = ProfileImage(
            context,
            sbn,
            chat
        )
        val logId = chat.getLong("chatLogId")

        val sender = MessageSenderImpl(
            chat,
            profileImage,
            sbn.packageName
        )

        val channel = MessageChannelImpl(
            chat,
            context,
            replyActionModel,
            readActionModel,
            sbn,
            project
        )

        return MessageEvent(
            message,
            sender,
            channel,
            sbn.packageName,
            logId,
            chat
        )
    }

}