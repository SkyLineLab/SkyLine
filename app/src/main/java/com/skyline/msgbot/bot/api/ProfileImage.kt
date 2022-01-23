/**
 * Created by naijun on 2022/01/23
 * Copyright (c) naijun.
 * This code is licensed under the MIT Licensing Principles.
 */

package com.skyline.msgbot.bot.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.service.notification.StatusBarNotification
import android.util.Base64
import java.io.ByteArrayOutputStream

class ProfileImage(var context: Context, var statusBarNotification: StatusBarNotification) {
    var hashCode: String = ""
    var profileImageDB: Bitmap? = null

    init {
        profileImageDB = getLargeIcon()
        if(profileImageDB !== null) hashCode = encodeBitmap(profileImageDB!!)
    }

    fun getProfileHash(): Int {
        return hashCode.hashCode()
    }

    fun getLargeIcon(): Bitmap? {
        return if (Build.VERSION.SDK_INT >= 23) {
            icon2Bitmap(context, statusBarNotification.notification.getLargeIcon())
        } else statusBarNotification.notification.largeIcon
    }

    private fun icon2Bitmap(context: Context, icon2: Icon?): Bitmap? {
        if (icon2 == null || Build.VERSION.SDK_INT < 23) return null
        val iconDrawable: Drawable = icon2.loadDrawable(context)
        val bitmap = Bitmap.createBitmap(
            iconDrawable.intrinsicWidth,
            iconDrawable.intrinsicHeight,
            if (iconDrawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap)
        iconDrawable.setBounds(0, 0, canvas.width, canvas.height)
        iconDrawable.draw(canvas)
        return bitmap
    }

    private fun encodeBitmap(bitmap: Bitmap): String {
        val BAOS = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, BAOS)
        return Base64.encodeToString(BAOS.toByteArray(), 0).trim()
    }
}