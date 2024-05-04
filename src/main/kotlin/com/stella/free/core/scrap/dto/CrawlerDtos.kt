package com.stella.free.core.scrap.dto


import com.stella.free.core.scrap.service.VideoDownloaderUtil
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File

import java.net.HttpURLConnection
import java.net.URI
import java.util.concurrent.Callable

data class UrlReqDto(
    val url:String,
)


data class ResultVO(
    val tsFileUrl: String,
    val tsFilePath: String,
    val isSuccess: Boolean,
    val tsFileAbsoluteUrl: String
)


// http call to file resource on website
internal class DownloaderTask(
    private val tsFileUrl: String, // path to save the video file on your computer
    private val tsFilePath: String,
) : Callable<ResultVO> {

    override fun call(): ResultVO {

        println("Begin downloading>>> $tsFileUrl Downloading on thread - ${Thread.currentThread().threadId()}")

        val tsUrl = URI(tsFileUrl).toURL()
        val connection = tsUrl.openConnection() as HttpURLConnection
        connection.connect()

        val urlFileLength = connection.getContentLength()

        connection.inputStream.use {

            val m3u8Bytes = IOUtils.toByteArray(it)
            val downloadedByteLength = m3u8Bytes.size

            val file = File(tsFilePath)

            FileUtils.writeByteArrayToFile(file, m3u8Bytes)
            val savedFileLength = file.length()

            println(tsFileUrl)
            println("urlFileLength: $urlFileLength downloadedByteLength: $downloadedByteLength savedFileLength: $savedFileLength")

            // sanity check. Did my file truly download and store completely?? - If not lets retry the download
            if (urlFileLength != downloadedByteLength || urlFileLength.toLong() != file.length()) {
                println("ERROR! Mismatching byte lengths! urlFileLength: $urlFileLength downloadedByteLength: $downloadedByteLength savedFileLength: $savedFileLength")
                println("Will retry download for $tsFileUrl")

                return ResultVO(tsFileUrl, tsFilePath, false, file.canonicalPath)
            }

            println("Successfully downloaded file: $tsFilePath")
            println(
                VideoDownloaderUtil.downloadCounter.getAndIncrement()
                    .toString() + " OUT OF " + VideoDownloaderUtil.filesToDownloadCount + " DOWNLOADED"
            )

            return ResultVO(tsFileUrl, tsFilePath, true, file.canonicalPath)
        }
    }


}