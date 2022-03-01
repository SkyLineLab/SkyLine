/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.utils

import com.skyline.msgbot.setting.Constants
import org.json.JSONObject
import java.io.File

/**
 * NPM Package
 *
 * @author naijun
 */
object NpmUtil {
    /**
     * 일단 의존성은 무시
     */
    fun getPackage(packageName: String) {
        Thread {
            val doc = HttpRequestUtil.request("https://registry.npmjs.org/$packageName")
            val json = JSONObject(doc);
            val name = json.getString("_id")
            val latest = json.getJSONObject("dist-tags").getString("latest")
            val versions = json.getJSONObject("versions")
            val latestObject = versions.getJSONObject(latest)
            val downloadUrl = latestObject.getJSONObject("dist").getString("tarball")
            HttpRequestUtil.downloadFile(downloadUrl, "/data/user/0/com.skyline.msgbot/files/lib/$name-${latest}.tar")
            TarUtil.unTar(
                File("/data/user/0/com.skyline.msgbot/files/lib/${name}-${latest}.tar"),
                File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/node_modules/")
            )
            File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/node_modules/package").renameTo(
                File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/node_modules/$name")
            )
        }.start()
    }
}