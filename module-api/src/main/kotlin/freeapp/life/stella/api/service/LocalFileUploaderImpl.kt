package freeapp.life.stella.api.service

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*



@Component
class LocalFileUploaderImpl(

)  {

    private val log = KotlinLogging.logger {  }


    val localImgFolderPath = "/src/main/resources/static/post"


    //@PostConstruct
    fun init() {

        val directory = File(localImgFolderPath)

        if (!directory.exists()) {
            log.debug ( "create directory $localImgFolderPath  ")
            Files.createDirectories(Paths.get(localImgFolderPath))
        }
    }



    fun upload(file: MultipartFile): String {

        val uuid = UUID.randomUUID().toString()
        val fileName = uuid + "_" +  file.originalFilename
        val imgFilePath = Paths.get("$localImgFolderPath/$fileName")

        Files.write(imgFilePath, file.bytes)
        val url = ResourceUtils.getURL(imgFilePath.toString())
        log.debug ("url==>$url" )
        return url.toString()
    }


}