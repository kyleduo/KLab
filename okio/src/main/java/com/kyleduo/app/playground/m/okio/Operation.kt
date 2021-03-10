package com.kyleduo.app.playground.m.okio

import okio.buffer
import okio.sink
import okio.source
import java.io.*

/**
 * @auther zhangduo on 3/10/21
 */

interface IOperator {

    fun readFile(file: File)

    fun copyFile(src: File, dst: File)
}

class JdkOperator : IOperator {
    override fun readFile(file: File) {
        val reader = BufferedInputStream(FileInputStream(file))
//        var line: String? = reader.readLine()
//        while (line != null) {
//            line = reader.readLine()
//        }

        val buffer = ByteArray(4096)
        var size = reader.read(buffer)
        while (size != -1) {
            size = reader.read(buffer)
        }
    }

    override fun copyFile(src: File, dst: File) {
        val reader = BufferedInputStream(FileInputStream(src))
        val writer = BufferedOutputStream(FileOutputStream(dst))
//        var line: String? = reader.readLine()
//        while (line != null) {
//            writer.write(line)
//            line = reader.readLine()
//        }

        val buffer = ByteArray(4096)
        var size = reader.read(buffer)
        while (size != -1) {
            writer.write(buffer, 0, size)
            size = reader.read(buffer)
        }
        writer.flush()
        writer.close()
    }

}

class OkioOperator : IOperator {
    override fun readFile(file: File) {
        val source = file.source().buffer()
//        while (!source.exhausted()) {
//            source.readUtf8Line()
//        }
//        var line: String? = source.readUtf8Line()
//        while (line != null) {
//            line = source.readUtf8Line()
//        }
        val buffer = ByteArray(4096)
        var size = source.read(buffer)
        while (size != -1) {
            size = source.read(buffer)
        }
    }

    override fun copyFile(src: File, dst: File) {
//        val source = src.source().buffer()
//        val sink = dst.sink().buffer()
//        while ((source.read(sink.buffer, 4096)) != -1L) {
//            sink.emitCompleteSegments()
//        }
//        val buffer = ByteArray(4096)
//        var size = source.read(buffer)
//        while (size != -1) {
//            sink.write(buffer, 0, size)
//            size = source.read(buffer)
//        }
////        source.readAll(sink)
//        sink.flush()

        src.source().use {
            dst.sink().buffer().writeAll(it)
        }
    }
}