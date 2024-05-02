package com.stella.free.core.scrap.service

import com.stella.free.global.util.logger
import java.io.File
import java.nio.file.Paths

class TestSeleniumService {

    private val log = logger()
    fun test(url: String) {

        val seleniumBMPInterceptor = SeleniumBMPInterceptor()
        val m3U8requestFiles =
            seleniumBMPInterceptor.retrieveM3U8requestFiles(url)



    }

    fun cleanUpVideoOutputDirectory(): Boolean {

        val downloadDirectory =
            Paths.get(".").toAbsolutePath().toUri().normalize().rawPath + "VideoOutputDirectory/"

        val files = File(downloadDirectory)
        val fileNames = files.list()

        for (fileName in fileNames) {
            if (fileName.endsWith(".ts")) {
                val fullFileName = downloadDirectory + fileName
                val currentFile: File = File(fullFileName)
                println("Deleting file " + currentFile.absolutePath)
                if (!currentFile.delete()) {
                    println("Failed to delete file $fileName")
                    return false
                }
            }
        }
        return true
    }


}