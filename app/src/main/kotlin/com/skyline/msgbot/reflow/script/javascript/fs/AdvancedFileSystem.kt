package com.skyline.msgbot.reflow.script.javascript.fs

import org.graalvm.polyglot.io.FileSystem
import java.net.URI
import java.nio.channels.SeekableByteChannel
import java.nio.file.AccessMode
import java.nio.file.DirectoryStream
import java.nio.file.LinkOption
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.attribute.FileAttribute

class AdvancedFileSystem : FileSystem {
    override fun parsePath(uri: URI?): Path {
        TODO("Not yet implemented")
    }

    override fun parsePath(path: String?): Path {
        TODO("Not yet implemented")
    }

    override fun checkAccess(
        path: Path?,
        modes: MutableSet<out AccessMode>?,
        vararg linkOptions: LinkOption?
    ) {
        TODO("Not yet implemented")
    }

    override fun createDirectory(dir: Path?, vararg attrs: FileAttribute<*>?) {
        TODO("Not yet implemented")
    }

    override fun delete(path: Path?) {
        TODO("Not yet implemented")
    }

    override fun newByteChannel(
        path: Path?,
        options: MutableSet<out OpenOption>?,
        vararg attrs: FileAttribute<*>?
    ): SeekableByteChannel {
        TODO("Not yet implemented")
    }

    override fun newDirectoryStream(
        dir: Path?,
        filter: DirectoryStream.Filter<in Path>?
    ): DirectoryStream<Path> {
        TODO("Not yet implemented")
    }

    override fun toAbsolutePath(path: Path?): Path {
        TODO("Not yet implemented")
    }

    override fun toRealPath(path: Path?, vararg linkOptions: LinkOption?): Path {
        TODO("Not yet implemented")
    }

    override fun readAttributes(
        path: Path?,
        attributes: String?,
        vararg options: LinkOption?
    ): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }
}