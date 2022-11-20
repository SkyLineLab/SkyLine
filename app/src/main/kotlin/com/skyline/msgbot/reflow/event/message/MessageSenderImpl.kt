package com.skyline.msgbot.reflow.event.message

import android.app.Person
import android.os.Build
import android.os.Bundle
import com.skyline.msgbot.reflow.App
import com.skyline.msgbot.reflow.internal.ProfileImage
import com.skyline.msgbot.reflow.util.ToStringUtil

class MessageSenderImpl(
    private val chatData: Bundle,
    private val profileImage: ProfileImage,
    private val packageName: String,
): MessageSender {

    override val name: String
        get() {
            return if (App.getPackageVersion(packageName) >= 9.7 && Build.VERSION.SDK_INT >= 29) {
                (chatData.get("android.messagingUser") as Person).name.toString()
            } else {
                chatData.getString("android.title")!!
            }
        }

    override val profileBase64: String
        get() = profileImage.hashCode

    override val profileHash: Int
        get() = profileImage.getProfileHash()

    override val userHash: String
        get() {
            return if (App.getPackageVersion(packageName) >= 9.7 && Build.VERSION.SDK_INT >= 30) {
                (((chatData.get("android.messages") as Array<*>)[0] as Bundle).get("sender_person") as Person).key.toString()
            } else {
                throw RuntimeException("userHash is not available on this environment")
            }
        }

    override fun toString(): String {
        return ToStringUtil.toStringFormat(this)
    }

}