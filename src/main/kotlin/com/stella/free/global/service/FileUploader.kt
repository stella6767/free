package com.stella.free.global.service

import org.springframework.web.multipart.MultipartFile

interface FileUploader {

    fun upload(file: MultipartFile): String
}