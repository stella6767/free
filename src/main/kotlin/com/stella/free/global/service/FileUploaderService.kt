package com.stella.free.global.service

import org.springframework.web.multipart.MultipartFile

interface FileUploaderService {

    fun upload(file: MultipartFile): String
}