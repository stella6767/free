package com.stella.free.core.scrap.dto


import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI
import java.util.concurrent.Callable


data class ResultVO(
    val tsFileUrl: String,
    val tsFilePath: String,
)


// http call to file resource on website
internal class DownloaderTask(
    private val tsFileUrl: String, // path to save the video file on your computer
    private val tsFilePath: String
) : Callable<ResultVO> {

    override fun call(): ResultVO {

        println(
            """
            Begin downloading>>> $tsFileUrl Downloading on thread - ${Thread.currentThread().name} 
            """.trimIndent()
        )

        var input: InputStream? = null
        val connection: HttpURLConnection
        var retryDownloadRequired = false
        try {

            val tsUrl = URI(tsFileUrl).toURL()
            connection = tsUrl.openConnection() as HttpURLConnection
            connection.connect()

            val urlFileLength = connection.getContentLength()
            input = connection.inputStream

            val m3u8Bytes: ByteArray = IOUtils.toByteArray(input)
            val downloadedByteLength = m3u8Bytes.size

            FileUtils.writeByteArrayToFile(File(tsFilePath), m3u8Bytes)

            val file = File(tsFilePath)
            val savedFileLength = file.length()

            println(tsFileUrl)
            println("urlFileLength: $urlFileLength downloadedByteLength: $downloadedByteLength savedFileLength: $savedFileLength")

            // sanity check. Did my file truly download and store completely?? - If not lets retry the download
            if (urlFileLength != downloadedByteLength || urlFileLength.toLong() != file.length()) {
                println("ERROR! Mismatching byte lengths! urlFileLength: $urlFileLength downloadedByteLength: $downloadedByteLength savedFileLength: $savedFileLength")
                println("Will retry download for $tsFileUrl")
                retryDownloadRequired = true
            }
        } catch (ex: Exception) {
            println("Error occurred")
        } finally {
            input?.close()
        }

//        // Return only Links (ResultVOs) that resulted in failed downloads
//        if (retryDownloadRequired) {
//            return ResultVO(tsFileUrl, tsFilePath)
//        } else {
//            println("Successfully downloaded file: $tsFilePath")
//            println(
//                VideoDownloaderUtil.downloadCounter.getAndIncrement()
//                    .toString() + " OUT OF " + VideoDownloaderUtil.filesToDownloadCount + " DOWNLOADED"
//            )
//            return null
//        }


        TODO()
    }
}