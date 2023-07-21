package com.stella.free.global.service

import com.stella.free.global.util.logger
import jakarta.annotation.PostConstruct
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class LocalFileUploaderImpl(

) : FileUploader {

    private val log = logger()


    val localImgFolderPath = "/src/main/resources/static/post"


    @PostConstruct
    fun init() {

        val directory = File(localImgFolderPath)

        if (!directory.exists()) {
            log.debug ( "create directory $localImgFolderPath  ")
            Files.createDirectories(Paths.get(localImgFolderPath))
        }
    }



    override fun upload(file: MultipartFile): String {

        val uuid = UUID.randomUUID().toString()
        val fileName = uuid + "_" +  file.originalFilename
        val imgFilePath = Paths.get("$localImgFolderPath/$fileName")

        Files.write(imgFilePath, file.bytes)
        val url = ResourceUtils.getURL(imgFilePath.toString())
        log.debug ("url==>$url" )
        return url.toString()
    }


}