package freeapp.life.stella.api.service.file

import org.springframework.web.multipart.MultipartFile

interface FileUploader {

    fun upload(file: MultipartFile): String
}