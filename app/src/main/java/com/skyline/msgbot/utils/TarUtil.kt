/**
 * Copyright (c) 2022 SkyLineLab
 *
 * PLEASE CHECK LICENSE THE LICENSE OF THE PROJECT REPOSITORY
 */

package com.skyline.msgbot.utils

import org.rauschig.jarchivelib.ArchiverFactory
import java.io.File

/**
 * 일단 tar 압축 풀기만 합니다
 * @see NpmUtil#getPackage
 *
 * @author naijun
 */
object TarUtil {
    fun unTar(sourceFile: File, dest: File) {
        val archiver = ArchiverFactory.createArchiver("tar", "gz")
        archiver.extract(sourceFile, dest)
    }
}