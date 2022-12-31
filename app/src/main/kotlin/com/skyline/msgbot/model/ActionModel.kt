package com.skyline.msgbot.model

import android.app.Notification
import com.skyline.msgbot.reflow.action.ActionType

data class ActionModel(
    val type: ActionType,
    val action: Notification.Action
)