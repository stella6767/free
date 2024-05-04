package com.stella.free.core.scrap.service

import com.stella.free.global.util.logger
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.UrlResource
import org.springframework.http.CacheControl
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Paths


@Service
class TestSeleniumService {

    private val log = logger()

    private val seleniumBMPInterceptor = SeleniumBMPInterceptor()
    private val videoDownloaderUtil = VideoDownloaderUtil()

    fun test(url: String) {

        val outputPath =
            Paths.get(".").toAbsolutePath().toUri().normalize().rawPath + "output/"

        println(outputPath)

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

        val downloadedFile =
            videoDownloaderUtil.downloaderUtil(directM3U8fileURL, outputPath)



        println("!!!!!! end !!!!!")
        println(downloadedFile.canonicalPath)


//        val resource = UrlResource("downloadFile.toURI()")
//
//        //인코딩 이슈
//        val sanitizedName =
//            URLEncoder.encode(
//                FilenameUtils.getBaseName("userMastering.originalFilename"),
//                StandardCharsets.UTF_8.toString()
//            )
//
//
//
//        ResponseEntity
//            .ok()
//            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//            .contentLength(resource.contentLength())
//            .cacheControl(CacheControl.noCache())
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${sanitizedName}.${fileType.name}\"")
//            .header("tmpName", URLEncoder.encode(downloadFile.canonicalPath, StandardCharsets.UTF_8.toString()))
//            .body(resource)

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