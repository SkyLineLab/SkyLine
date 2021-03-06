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

package com.skyline.msgbot.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

/**
 * 봇앱에서 필요한 권한을 요청합니다
 * 곧 리펙토링 예정
 */
object PermissionUtil {

    fun getAccessNotification(context: Context): Boolean {
        return NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)
    }

    fun requestAccessNotification(context: Context, req: Boolean): Boolean {
        return if(!getAccessNotification(context)) {
            if(req) {
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            false
        } else true
    }


    fun requestIgnoreBatteryOptimizations(context: Context, req: Boolean): Boolean {
        val powerCheck = context.getSystemService(ComponentActivity.POWER_SERVICE) as PowerManager
        return if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                powerCheck.isIgnoringBatteryOptimizations(context.packageName)
            } else {
                return false
            }
        ) true
        else {
            if (req){
                val intent = Intent()
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:${context.packageName}")
                context.startActivity(intent)
            }
            false
        }
    }

    fun requestStoragePermissions(context: Context, req: Boolean): Boolean {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return if(Environment.isExternalStorageManager()) {
                true
            } else {
                if (req) {
                    context.startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
                }
                false
            }
        } else {
            return if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                true
            } else {
                if(req) {
                    requestPermissions(
                        context as Activity,
                        permissions, 0
                    )
                }
                false
            }
        }
    }

    fun requestAllPermision(context: Context, request: Boolean): Boolean {
        return requestStoragePermissions(context, request) && requestAccessNotification(context, request) && requestIgnoreBatteryOptimizations(context, request)
    }

}