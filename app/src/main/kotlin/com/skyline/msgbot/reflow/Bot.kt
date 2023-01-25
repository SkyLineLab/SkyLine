package com.skyline.msgbot.reflow

import android.os.Bundle
import android.service.notification.StatusBarNotification
import com.orhanobut.logger.Logger
import com.skyline.msgbot.model.ActionModel
import com.skyline.msgbot.reflow.event.Event
import com.skyline.msgbot.reflow.event.message.MessageEventUtil
import com.skyline.msgbot.reflow.event.power.PowerEvent
import com.skyline.msgbot.reflow.event.project.ProjectEvent
import com.skyline.msgbot.reflow.project.ProjectManager
import com.skyline.msgbot.reflow.session.ChannelSession

object Bot {

    fun callMessageEvent(sbn: StatusBarNotification, chat: Bundle, replyActionModel: ActionModel, readActionModel: ActionModel) {
        Logger.d("call message")
        ProjectManager.list.forEach { (name, project) ->
            val event = MessageEventUtil.createMessageEvent(
                sbn,
                chat,
                replyActionModel,
                readActionModel,
                project
            )

            ChannelSession.addSession(event.room)

            Logger.d("emit!")

            project.client.emit(
                Event.ON_MESSAGE,
                event
            )
        }
    }

    fun callPowerEvent(event: PowerEvent) {

    }

    fun callProjectEvent(event: ProjectEvent) {

    }

}