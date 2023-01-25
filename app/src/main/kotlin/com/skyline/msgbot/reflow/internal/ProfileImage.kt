package com.skyline.msgbot.reflow.internal

import android.app.Person
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.service.notification.StatusBarNotification
import android.util.Base64
import com.skyline.msgbot.reflow.App
import java.io.ByteArrayOutputStream

class ProfileImage(
    private var context: Context,
    private var sbn: StatusBarNotification,
    bundle: Bundle
) {

    var hashCode: String = ""
    private var profileImageDB: Bitmap? = null

    init {
        profileImageDB = if ((App.getPackageVersion(sbn.packageName) >= 9.7) && Build.VERSION.SDK_INT >= 29) {
            icon2Bitmap(
                context,
                (bundle.getParcelableArray("android.messages")!![0] as Bundle)
                    .getParcelable<Person>("sender_person")!!.icon
            )
        } else {
            getLargeIcon()
        }
        if (profileImageDB !== null) hashCode = encodeBitmap(profileImageDB!!)
    }

    fun getProfileHash(): Int {
        return hashCode.hashCode()
    }

    private fun getLargeIcon(): Bitmap? {
        return icon2Bitmap(context, sbn.notification.getLargeIcon())
    }

    private fun encodeBitmap(bitmap: Bitmap): String {
        val BAOS = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, BAOS)
        return Base64.encodeToString(BAOS.toByteArray(), 0).trim()
    }

    private fun icon2Bitmap(context: Context, icon: Icon?): Bitmap? {
        if (icon == null) return null
        val iconDrawable: Drawable = icon.loadDrawable(context) ?: return null

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

}