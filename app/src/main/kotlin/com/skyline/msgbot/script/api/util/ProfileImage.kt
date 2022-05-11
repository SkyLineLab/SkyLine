/*
 * MIT License
 *
 * Copyright (c) 2022 SkyLineLab
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.skyline.msgbot.script.api.util

import android.app.Person
import android.graphics.Bitmap
import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.service.notification.StatusBarNotification
import android.util.Base64
import com.skyline.msgbot.util.AppUtil
import java.io.ByteArrayOutputStream

class ProfileImage(
    var context: Context,
    var statusBarNotification: StatusBarNotification,
    var bundle: Bundle,
) {
    var hashCode: String = ""
    var profileImageDB: Bitmap? = null

    init {
        profileImageDB = if ((AppUtil.getPackageVersion(statusBarNotification.packageName) >= 9.7) && Build.VERSION.SDK_INT >= 29) {
            icon2Bitmap(context, ((bundle.getParcelableArray("android.messages")!![0] as Bundle).getParcelable<Person>("sender_person")?.icon))
        } else {
            getLargeIcon()
        }
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