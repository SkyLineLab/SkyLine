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
import com.skyline.msgbot.model.ActionModel
import com.skyline.msgbot.reflow.App
import com.skyline.msgbot.reflow.project.Project
import com.skyline.msgbot.reflow.script.javascript.JSPromise
import com.skyline.msgbot.reflow.session.ChannelSession
import com.skyline.msgbot.reflow.util.KakaoTalkUtil
import com.skyline.msgbot.reflow.util.ToStringUtil
import org.graalvm.polyglot.Value
import java.util.concurrent.CompletableFuture

class MessageChannelImpl(
    private val bundle: Bundle,
    private val context: Context,
    private val replyActionModel: ActionModel,
    private val readActionModel: ActionModel,
    private val statusBarNotification: StatusBarNotification,
    private val project: Project
) : MessageChannel {

    override val name: String
        get() {
            return if (KakaoTalkUtil.isNewStruct(statusBarNotification.packageName)) {
                bundle.getString("android.subText") ?: (bundle.getString("android.title") ?: "")
            } else {
                bundle.getString("android.summaryText") ?: (bundle.getString("android.title") ?: "")
            }
        }

    override val isGroupChat: Boolean
        get() {
            return if (KakaoTalkUtil.isNewStruct(statusBarNotification.packageName)) {
                bundle.getBoolean("android.isGroupConversation")
            } else {
                name == bundle.getString("android.summaryText")
            }
        }

    override val channelId: Long
        get() = statusBarNotification.tag.toLong()

    override fun send(message: Any?): Any {
        val future: CompletableFuture<Any> = CompletableFuture.supplyAsync {
            val result = when (message) {
                is Value -> { // graalvm_js
                    message.toString()
                }
                is V8Value -> { // v8
                    message.toString()
                }
                else -> { // else
                    message.toString()
                }
            }

            val sendIntent = Intent(Intent.ACTION_SEND)
            val msg = Bundle()
            val actualInputs = ArrayList<RemoteInput>()

            try {
                for (inputable in replyActionModel.action.remoteInputs) {
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

            replyActionModel.action.actionIntent.send(context, 0, sendIntent)

            return@supplyAsync true
        }

        return JSPromise.createPromiseObject(
            project,
            future
        )
    }

    override fun sendAllRoom(message: Any?): Any {
        val future: CompletableFuture<Any> = CompletableFuture.supplyAsync {
            ChannelSession.sessions.forEach {
                it.value.send(message)
            }
        }

        return JSPromise.createPromiseObject(
            project,
            future
        )
    }

    override fun markAsRead(): Any {
        val future: CompletableFuture<Any> = CompletableFuture.supplyAsync {
            readActionModel.action.actionIntent.send(context, 1, Intent())

            return@supplyAsync true
        }

        return JSPromise.createPromiseObject(
            project,
            future
        )
    }

    override fun toString(): String {
        return ToStringUtil.toStringFormat(this)
    }

}