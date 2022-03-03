/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.utils

import android.annotation.SuppressLint
import com.skyline.msgbot.bot.api.FileStream
import com.skyline.msgbot.setting.Constants
import kotlinx.coroutines.runBlocking
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
    @SuppressLint("SdCardPath")
    fun getPackage(packageName: String, project: String) {
        val packageString = FileStream.read("Projects/$project/package.json") ?: return
        val packageJson = JSONObject(packageString)
        println(packageJson.toString(2))
        if (packageJson.getJSONObject("dependencies").has(packageName)) return
        println("call package = $packageName")
        val doc = HttpRequestUtil.request("https://registry.npmjs.org/$packageName")
        val json = JSONObject(doc);
        val name = json.getString("_id")
        val latest = json.getJSONObject("dist-tags").getString("latest")
        val versions = json.getJSONObject("versions")
        val latestObject = versions.getJSONObject(latest)
        var dependencies: JSONObject? = null
        if (latestObject.has("dependencies")) {
            dependencies = latestObject.getJSONObject("dependencies")
            val dependenciesArray = dependencies!!.keys()
            dependenciesArray.forEach {
                println(it)
                getPackage(it, project)
            }
        }
        val downloadUrl = latestObject.getJSONObject("dist").getString("tarball")
        HttpRequestUtil.downloadFile(downloadUrl, "/data/user/0/com.skyline.msgbot/files/lib/$name-${latest}.tar")
        TarUtil.unTar(
            File("/data/user/0/com.skyline.msgbot/files/lib/${name}-${latest}.tar"),
            File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/node_modules/")
        )
        File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/node_modules/package").renameTo(
            File("${SDCardUtils.sdcardPath}/${Constants.directoryName}/Projects/$project/node_modules/$name")
        )
        FileStream.remove("/data/user/0/com.skyline.msgbot/files/lib/$name-${latest}.tar")
        packageJson.getJSONObject("dependencies").put(name, latest)
        val newPackageJson = packageJson.toString(4)
        FileStream.write("Projects/$project/package.json", newPackageJson)
    }
}