package com.stella.free.core.scrap.service

import com.stella.free.global.util.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.io.File
import java.nio.file.Paths


@Service
class TestSeleniumService {

    private val log = logger()

    private val seleniumBMPInterceptor = SeleniumBMPInterceptor()
    private val videoDownloaderUtil = VideoDownloaderUtil()

    @Value("\${chrome.driver}")
    private val driverPath: String = ""

    @Value("\${outputPath}")
    private val outputPath: String = "/Users/stella6767/Downloads/images/Engineer-Mail-01/Received/"

    fun test(url: String) {

        if (!cleanUpOutputDirectory(outputPath)) {
            log.error("Failed to clean up files in VideoOutputDirectory");
            return
        }

        val m3U8requestFiles =
            seleniumBMPInterceptor.retrieveM3U8requestFiles(url)

        if (m3U8requestFiles.isEmpty()) {
            log.error(
                "ERROR! No http requests for m3u8 files were found while searching website --> " + m3U8requestFiles +
                        "\nPlease provide the direct m3u8 URL."
            )
            return
        }

        val masterM3U = videoDownloaderUtil.findMasterM3U(m3U8requestFiles)

        val directM3U8fileURL = if (!StringUtils.hasLength(masterM3U)) {
            log.info(
                "WARNING!! WARNING!! Did not find a master playlist\n" +
                        "The first m3u8 url file found will be used to attempt video download --> " + m3U8requestFiles
            )
            m3U8requestFiles.first()
        } else {
            val highestBitRateVariant = videoDownloaderUtil.getM3U8variantWithHighestBitrate(masterM3U)
            log.info("FOUND HIGHEST BIT RATE URL = $highestBitRateVariant")
            highestBitRateVariant
        }

        if (!StringUtils.hasLength(directM3U8fileURL)) {
            log.error("ERROR!!!ERROR!!! No M3U8 file url was provide/found")
            return
        }

        videoDownloaderUtil.downloaderUtil(directM3U8fileURL, outputPath)
    }


    fun cleanUpOutputDirectory(downloadDirectory: String): Boolean {

        val files = File(downloadDirectory)
        val fileNames = files.list()

        for (fileName in fileNames) {
            if (fileName.endsWith(".ts") || fileName.endsWith(".txt")) {
                val fullFileName = downloadDirectory + fileName
                val currentFile = File(fullFileName)
                log.info("Deleting file " + currentFile.absolutePath)
                if (!currentFile.delete()) {
                    log.info("Failed to delete file $fileName")
                    return false
                }
            }
        }
        return true
    }


}