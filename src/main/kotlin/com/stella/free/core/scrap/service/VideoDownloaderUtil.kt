//package com.stella.free.core.scrap.service
//
//import com.stella.free.core.scrap.dto.DownloaderTask
//import com.stella.free.core.scrap.dto.ResultVO
//import com.stella.free.global.util.logger
//import org.apache.commons.io.IOUtils
//import org.springframework.util.CollectionUtils
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStreamReader
//import java.net.URI
//import java.net.URL
//import java.nio.charset.StandardCharsets
//import java.nio.file.Paths
//import java.util.*
//import java.util.concurrent.Executors
//import java.util.concurrent.Future
//import java.util.concurrent.atomic.AtomicInteger
//import java.util.regex.Matcher
//import java.util.regex.Pattern
//
//
//class VideoDownloaderUtil() {
//
//    private val log = logger()
//
//    fun downloaderUtil(m3u8FileURL: String) {
//
//        // Download m3u8 description file and return a list of its .ts url contents
//        var m3u8TsfilesList = this.processM3U8urlFile(m3u8FileURL)
//
//        if (m3u8TsfilesList.isEmpty()) {
//            System.err.println("ERROR!!!ERROR!!! - No TS files... Exiting >>>$m3u8FileURL")
//            return
//        } else {
//            // Check if the extracted urls are relative or absolute paths
//            val tsVideoUrl = m3u8TsfilesList[0]
//
//            if (!tsVideoUrl.startsWith("http:") && !tsVideoUrl.startsWith("https:")) {
//                println(
//                    "WARN! WARN! The following file location is not an absolute path URL!\n" +
//                            " Current relative path = " + tsVideoUrl + " Absolute path will be based on current on the M3U8 file path: " + m3u8FileURL
//                )
//                val absoluteM3u8TsFilesList = mutableListOf<String>()
//                for (relativeTSurls: String in m3u8TsfilesList) {
//                    // Using M3U8 url to construct absolute paths for ts file urls
//                    val absoluteTS = getAbsolutePath(m3u8FileURL, relativeTSurls)
//                    absoluteM3u8TsFilesList.add(absoluteTS)
//                }
//                println("New absolute file path url" + absoluteM3u8TsFilesList[0])
//                m3u8TsfilesList = absoluteM3u8TsFilesList
//            }
//        }
//
//        filesToDownloadCount = m3u8TsfilesList.size
//
//        // Video files will be downloaded to  <projectDirectory>/java-video-downloader-util/VideoOutputDirectory/.
//        val download: String =
//            Paths.get(".").toAbsolutePath().toUri().normalize().getRawPath() + "VideoOutputDirectory/"
//
//        val downloadDirectory = if (download.startsWith("\\") || download.startsWith("/")) {
//            download.substring(1)
//        } else {
//            download
//        }
//
//        println("\nWARNING!! WARNING!! Will overwrite existing Full_Video.ts\n")
//        println("Download videos to directory >> $downloadDirectory\n")
//
//        // The ffmpeg tool requires the input string formatted as:  "filename_1|filename_2|filename_3|filename_n"
//        // TsFile names must remain in the same natural order present in .m3u8 file
//        // WARNING: Maximum command length is 8191 characters for a Windows OS Command prompt (Cmd.exe)
//        // Break up command string in multiple parts if command were to exceed char limit
//        val ffmpegCommandBuilderList: Queue<StringBuilder> = LinkedList()
//
//        var tsFileNamesBuilder = StringBuilder()
//        val tsLinkVsPathHashmap: MutableMap<String, String> = HashMap()
//
//        for (tsLink: String in m3u8TsfilesList) {
//            val p = Pattern.compile("[^\\/]+(?=\\.ts).ts") // the pattern to search for
//            val m: Matcher = p.matcher(tsLink)
//            var videoName: String? = null
//            if (m.find()) {
//                videoName = m.group(0)
//            }
//            val tsFilePath = downloadDirectory + videoName
//            tsLinkVsPathHashmap[tsLink] = tsFilePath
//
//            tsFileNamesBuilder.append(tsFilePath).append("|")
//            if (tsFileNamesBuilder.length > 7900) { // Don't exceed 8191 cmd limit
//                println("WARNING: Exceeded character length >> " + tsFileNamesBuilder.length)
//                ffmpegCommandBuilderList.add(tsFileNamesBuilder)
//                tsFileNamesBuilder = StringBuilder()
//            }
//            println("Ts file path appended: $tsFilePath")
//        }
//        // Add last or first part of builder depending on builder string length
//        ffmpegCommandBuilderList.add(tsFileNamesBuilder)
//
//        System.out.println("Total number of command ts pieces " + ffmpegCommandBuilderList.size)
//        // remove the last trailing pipe "|" character in the input string
//        for (builder: StringBuilder in ffmpegCommandBuilderList) {
//            builder.deleteCharAt(builder.length - 1)
//        }
//
//        // Download and save all 'ts' files. 'NULL' is returned if ALL files were successfully downloaded.
//        // Else, failed files will be retried.
//        var retryDownloadsList: List<ResultVO> = downloadAndStoreFiles(m3u8TsfilesList, tsLinkVsPathHashmap, null)
//
//        // Failed files will be retired 3 times.
//        if (CollectionUtils.isEmpty(retryDownloadsList)) {
//            println("All files successfully downloaded no need to retry any! :)")
//        } else {
//            System.err.println("ERROR!!! ERROR!!! Some files were NOT successfully downloaded. Will retry for " + retryDownloadsList!!.size + " file(s)")
//            var retryTaskList = getDownloaderTaskRetryList(retryDownloadsList)
//            var retryCounter = 0
//            do {
//                retryDownloadsList = downloadAndStoreFiles(m3u8TsfilesList, null, retryTaskList)
//                if (!CollectionUtils.isEmpty(retryDownloadsList)) {
//                    retryTaskList = getDownloaderTaskRetryList(retryDownloadsList)
//                }
//                retryCounter++
//            } while ((!CollectionUtils.isEmpty(retryDownloadsList)) && retryCounter != 3)
//
//            if (retryCounter == 3) {
//                println(
//                    "ERROR!!! ERROR!!! ERROR!!!! - After more than 3 attempts some files did NOT successfully download. \n" +
//                            "ERROR!!! ERROR!!! ERROR!!!! - Your video may NOT yield the expected result or ffmpeg tool can fail"
//                )
//            }
//        }
//
//        var fullVideo: String? = null
//
//        // Prepare and execute ts video concatenation
//        var pieceCount = 0
//        while (!ffmpegCommandBuilderList.isEmpty()) {
//            var outputFilePath = downloadDirectory + "piece_" + pieceCount + ".ts"
//
//            if (ffmpegCommandBuilderList.size === 1) {
//                outputFilePath = downloadDirectory + "Full_Video.ts"
//                fullVideo = outputFilePath
//                println("Full output video name = $outputFilePath")
//            }
//
//            executeFFMPEG(outputFilePath, ffmpegCommandBuilderList.poll().toString())
//
//            if (null != ffmpegCommandBuilderList.peek()) {
//                val nextCommandStr: StringBuilder = ffmpegCommandBuilderList.peek()
//                val tmpOutputFilePath = "$outputFilePath|"
//                nextCommandStr.insert(0, tmpOutputFilePath)
//                println("Modified command next iteration to be >> $nextCommandStr")
//                pieceCount++
//            }
//        }
//
//
//        println("\n================== Video download finished for $m3u8FileURL ==================")
//        println("\n================== Full Video @ $fullVideo ==================")
//        // reset counter
//        totalDownloadCounter.getAndAdd(downloadCounter.decrementAndGet())
//        downloadCounter.set(1)
//    }
//
//    private fun executeFFMPEG(outputFilePath: String, ffmpegInputFileNames: String) {
//
//        println("\nExecuting ffmpeg with output path = $outputFilePath\n")
//        println("Input ffmpegInputFileNames character length >> " + ffmpegInputFileNames.length)
//        // Build ffmpeg tool command and execute
//        // ffmpeg will concat all our *.ts files and produce a single output video file
//        val processBuilder = ProcessBuilder()
//        val commandStr = "ffmpeg -i \"concat:$ffmpegInputFileNames\" -c copy $outputFilePath"
//        // Run this on Windows, cmd, /c = terminate after this run
//        processBuilder.command("cmd.exe", "/c", commandStr)
//        processBuilder.redirectErrorStream(true)
//
//        try {
//            println("Executing command - $commandStr")
//            val process = processBuilder.start()
//
//            // Let's read and print the ffmpeg's output
//            val r = BufferedReader(InputStreamReader(process.inputStream))
//            var line: String?
//            while (true) {
//                line = r.readLine()
//                if (line == null) {
//                    break
//                }
//                println(line)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    /*
//     * Download *.m3u8 file.
//     * Find and get all *.ts file names in m3u8 file (These should be listed in sequential order)
//     * Return list of *.ts file urls
//     */
//
//    private fun processM3U8urlFile(m3u8Url: String): List<String> {
//
//        val url = URI(m3u8Url).toURL()
//        val m3u8List = mutableListOf<String>()
//
//        url.openStream().use { `is` ->
//            val m3u8File: String = IOUtils.toString(`is`, StandardCharsets.UTF_8)
//            // Find and get all *.ts file names in m3u8 file
//            val m =
//                Pattern.compile(".*\\.ts.*", Pattern.MULTILINE).matcher(m3u8File)
//
//            val isRelativePath = false
//            while (m.find()) {
//                val tsVideoUrl: String = m.group()
//                m3u8List.add(tsVideoUrl)
//            }
//        }
//
//        println("Total ts files to be downloaded ${m3u8List.size}")
//        return m3u8List
//    }
//
//    /*
//     * Divide and conquer the video file downloads
//     * Method also handles download retries if the 'retryTaskList' is not empty/null
//     */
//    private fun downloadAndStoreFiles(
//        m3u8List: List<String>,
//        linkVsFilePath: Map<String, String>,
//        retryTaskList: MutableList<DownloaderTask>
//    ): List<ResultVO> {
//
//        val executor = Executors.newFixedThreadPool(5)
//        val taskList: MutableList<DownloaderTask>
//
//        // Check if this is a retry download task
//        if (!CollectionUtils.isEmpty(retryTaskList)) {
//            taskList = retryTaskList
//        } else {
//            taskList = ArrayList<DownloaderTask>()
//            for (tsUrlLink: String in m3u8List) {
//                val tsFilePath = linkVsFilePath[tsUrlLink].toString()
//                val downloaderTask = DownloaderTask(tsUrlLink, tsFilePath)
//                taskList.add(downloaderTask)
//            }
//        }
//
//        // Execute all downloader tasks and get reference to Future objects
//        var resultList: List<Future<ResultVO>> = executor.invokeAll(taskList)
//        executor.shutdown()
//
//        val failedResultsList: MutableList<ResultVO> = ArrayList<ResultVO>()
//
//        for (future: Future<ResultVO> in resultList) {
//            val result: ResultVO = future.get()
//            if (null != result) {
//                println("Failed file details: $result")
//                failedResultsList.add(result)
//            }
//        }
//
//        if (CollectionUtils.isEmpty(failedResultsList)) return emptyList()
//
//        println("===================== A total of " + failedResultsList.size + " failed to download =====================")
//        return failedResultsList
//    }
//
//    /*
//     * Create and get the Retry tasks for previously unsuccessful downloads
//     */
//    private fun getDownloaderTaskRetryList(retryDownloadsList: List<ResultVO>): MutableList<DownloaderTask> {
//        println("\n===================== Retrying the following file links =====================")
//        var fileCounter = 1
//        val retryTaskList: MutableList<DownloaderTask> = ArrayList<DownloaderTask>()
//        for (resultVO: ResultVO in retryDownloadsList) {
//            println(fileCounter.toString() + ". " + resultVO.tsFileUrl)
//            val downloaderTask = DownloaderTask(resultVO.tsFileUrl, resultVO.tsFilePath)
//            retryTaskList.add(downloaderTask)
//            fileCounter++
//        }
//        return retryTaskList
//    }
//
//    /*
//     * Master/Playlist files can be identified if they contain the "EXT-X-STREAM-INF" tag
//     */
//    fun findMasterM3U(listOfHarM3u8files: List<String>): String {
//
//        println("Inspecting list of HAR m3u8 files to identify mater playlist")
//        for ((counter, m3uFileUrl: String) in listOfHarM3u8files.withIndex()) {
//            var url = URI(m3uFileUrl).toURL()
//            url.openStream().use { `is` ->
//                val playlistFileIO: String = IOUtils.toString(`is`, StandardCharsets.UTF_8)
//                println("\n$counter. Printing content of m3u8 file\n$playlistFileIO")
//                if (playlistFileIO.contains("EXT-X-STREAM-INF")) {
//                    println("FOUND Master/Playlist m3u8 url: $m3uFileUrl")
//                    return m3uFileUrl
//                }
//            }
//        }
//
//        println("WARNING! No m3u8 master playlist found!")
//        return ""
//    }
//
//    /*
//     * Multiple M3U8 file variants exist so find the highest quality one.
//     */
//    fun getM3U8variantWithHighestBitrate(masterPlaylistUrl: String): String {
//
//        println("Finding highest bitrate in Master/Playlist file URL >>> $masterPlaylistUrl " )
//
//        val url = URI(masterPlaylistUrl).toURL()
//
//        var highestBitRateM3U8url = url.openStream().use { `is` ->
//
//            val playlistFileIO: String = IOUtils.toString(`is`, StandardCharsets.UTF_8)
//            println("Printing content of master playlist file >>> $playlistFileIO")
//            // Find all bit rate variants
//            val bitrateMatcher =
//                Pattern.compile("[^AVERAGE-]BANDWIDTH=([0-9]+)", Pattern.MULTILINE).matcher(playlistFileIO)
//            // Find and get all *.m3u8 file paths
//            val m3u8Matcher =
//                Pattern.compile("^.*\\.m3u8.*", Pattern.MULTILINE).matcher(playlistFileIO)
//
//            var highestBitRate = 0
//            var highestBitRateIdx = 0
//            var idxCounter = 0
//
//            println("Finding highest bitrate...")
//
//            while (bitrateMatcher.find()) {
//
//                val tempBitRate =
//                    bitrateMatcher.group(1).toInt()
//
//                println("tempBitRate - $tempBitRate")
//
//                if (highestBitRate < tempBitRate) {
//                    highestBitRate = tempBitRate
//                    highestBitRateIdx = idxCounter
//                    println("New highestBitRate - $highestBitRate at index - $highestBitRateIdx")
//                }
//
//                idxCounter++
//            }
//
//            val m3u8MatcherList: MutableList<String> = ArrayList()
//            while (m3u8Matcher.find()) {
//                println("m3u8Matcher - " + m3u8Matcher.group())
//                m3u8MatcherList.add(m3u8Matcher.group())
//            }
//            println("Highest bit rate m3u8 match - ${m3u8MatcherList[highestBitRateIdx]}")
//
//            m3u8MatcherList[highestBitRateIdx]
//        }
//
//
//        if (!highestBitRateM3U8url.startsWith("http:") && !highestBitRateM3U8url.startsWith("https:")) {
//
//            println(
//                ("WARN! WARN! The following file location is not an absolute path URL!\n" +
//                        " Current relative path = " + highestBitRateM3U8url + " Absolute path will be based on current on the M3U8 file path: " + masterPlaylistUrl)
//            )
//            // Using M3U8 url to construct absolute paths for ts file urls
//            highestBitRateM3U8url = getAbsolutePath(masterPlaylistUrl, highestBitRateM3U8url)
//            println("New absolute file path url$highestBitRateM3U8url")
//        }
//
//        return highestBitRateM3U8url
//    }
//
//
//    private fun getAbsolutePath(baseAbsolutePath: String, relativePath: String?): String {
//        val idx = baseAbsolutePath.lastIndexOf("/")
//        val base = baseAbsolutePath.substring(0, idx + 1)
//        val absolutePath = base + relativePath
//        return absolutePath
//    }
//
//    companion object {
//        var downloadCounter: AtomicInteger = AtomicInteger(1)
//        var totalDownloadCounter: AtomicInteger = AtomicInteger(0)
//        var filesToDownloadCount: Int = 0
//    }
//}