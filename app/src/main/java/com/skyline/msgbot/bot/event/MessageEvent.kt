/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.bot.event

import android.app.Notification
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.notification.StatusBarNotification
import org.graalvm.polyglot.Value

class BotChannel(
    private val bundle: Bundle,
    private val context: Context,
    private val session: Notification.Action,
    private val statusBarNotification: StatusBarNotification
) : ChatChannel {
    override val name: String = bundle.getString("android.summaryText") ?: (bundle.getString("android.title") ?: "")

    override val isGroupChat: Boolean = name == bundle.getString("android.summaryText")

    override fun send(message: Value?): Boolean {
        val sendIntent = Intent()
        val msg = Bundle()
        for (inputable in session.remoteInputs)
            msg.putCharSequence(inputable.resultKey, message?.`as`(Any::class.java).toString())
        RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, msg)
        return try {
            session.actionIntent.send(context, 0, sendIntent)
            true
        } catch (e: PendingIntent.CanceledException) {
            false
        }
    }

    override fun markAsRead(): Boolean {
        return try {
            statusBarNotification.notification.actions[0].actionIntent.send(context, 1, Intent())
            true
        }
        catch (e: PendingIntent.CanceledException){
            false
        }
    }
}

data class MessageEvent(
    val message: String,
    val sender: ChatSender,
    val room: ChatChannel,
    val packageName: String
)

interface ChatChannel {
    val name: String

    val isGroupChat: Boolean

    fun send(message: Value?): Boolean

    fun markAsRead(): Boolean
}

data class ChatSender(
    val name: String,
    val profileBase64: String,
    val profileHash: Int
)