package com.skyline.msgbot.utils

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
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

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
                Toast.makeText(context, "알림 읽기 권한이 없습니다 :( 권한을 허용해주세요", Toast.LENGTH_LONG).show()
            }
            false
        } else true
    }


    fun requestIgnoreBatteryOptimizations(context: Context, req: Boolean): Boolean {
        val powerCheck = context.getSystemService(ComponentActivity.POWER_SERVICE) as PowerManager
        return if(powerCheck.isIgnoringBatteryOptimizations(context.packageName)) true
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
                    Toast.makeText(context, "파일 권한이 없습니다 :( 권한을 허용해주세요", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(context, "파일 권한이 없습니다 :( 권한을 허용해주세요", Toast.LENGTH_LONG).show()
                }
                false
            }
        }
    }

    fun requestAllPermision(context: Context, request: Boolean): Boolean {
        return requestStoragePermissions(context, request) && requestAccessNotification(context, request) && requestIgnoreBatteryOptimizations(context, request)
    }
}