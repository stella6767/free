package com.stella.free.core.scrap.service

import com.stella.free.global.util.logger
import java.io.File
import java.nio.file.Paths

class TestSeleniumService {

    private val log = logger()
    fun test(url: String) {




    }

    fun cleanUpOutputDirectory(): Boolean {

        val downloadDirectory =
            Paths.get(".").toAbsolutePath().toUri().normalize().rawPath + "output/"

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