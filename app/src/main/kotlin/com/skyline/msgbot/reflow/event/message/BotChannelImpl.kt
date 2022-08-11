package com.skyline.msgbot.reflow.event.message

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.notification.StatusBarNotification
import com.caoccao.javet.values.V8Value
import com.orhanobut.logger.Logger
import com.skyline.msgbot.reflow.App
import com.skyline.msgbot.reflow.util.ToStringUtil
import org.graalvm.polyglot.Value

class BotChannelImpl(
    private val bundle: Bundle,
    private val context: Context,
    private val session: Notification.Action,
    private val statusBarNotification: StatusBarNotification
) : BotChannel {

    override val name: String
        get() {
            return if (App.getPackageVersion(statusBarNotification.packageName) >= 9.7 && Build.VERSION.SDK_INT >= 29) {
                bundle.getString("android.subText") ?: (bundle.getString("android.title") ?: "")
            } else {
                bundle.getString("android.summaryText") ?: (bundle.getString("android.title") ?: "")
            }
        }

    override val isGroupChat: Boolean
        get() {
            return if (App.getPackageVersion(statusBarNotification.packageName) >= 9.7 && Build.VERSION.SDK_INT >= 29) {
                bundle.getBoolean("android.isGroupConversation")
            } else {
                name == bundle.getString("android.summaryText")
            }
        }

    override fun send(message: Any?) {
        val result = when (message) {
            is Value -> { // graalvm_js
                message.toString()
            }
            is V8Value -> { // v8
                message.toString()
            }
            else -> { // rhino
                message.toString()
            }
        }

        val sendIntent = Intent(Intent.ACTION_SEND)
        val msg = Bundle()
        val actualInputs = ArrayList<RemoteInput>()

        try {
            for (inputable in session.remoteInputs) {
                msg.putCharSequence(inputable.resultKey, result)

                val builder = RemoteInput.Builder(inputable.resultKey)
                builder.setLabel(inputable.label)
                builder.setChoices(inputable.choices)
                builder.setAllowFreeFormInput(inputable.allowFreeFormInput)
                builder.addExtras(inputable.extras)
                actualInputs.add(builder.build())
            }
        } catch (e: Exception) {
            Logger.e("Send Error: ${e.message} stackTrace = ${e.stackTraceToString()}")
        }

        RemoteInput.addResultsToIntent(
            actualInputs.toTypedArray(),
            sendIntent,
            msg
        )

        session.actionIntent.send(context, 0, sendIntent)
    }

    override fun sendAllRoom(message: Any?): Any {
        TODO("Not yet implemented")
    }

    override fun markAsRead(): Any {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return ToStringUtil.toStringFormat(this)
    }

}