package com.stella.free.core.scrap.service

import com.stella.free.global.util.logger
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.MatOfRect
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption


@Service
class OpenCvService(

) {

    private val log = logger()

    init {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }


    fun imgToGrace(img: MultipartFile){

        val tempFile =
            File.createTempFile("temp_image", ".jpg")
        Files.copy(img.inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING)

        val inputImageMat = Imgcodecs.imread(tempFile.absolutePath)

        val filteredImageMat = Mat()
        Imgproc.GaussianBlur(inputImageMat, filteredImageMat, org.opencv.core.Size(5.0, 5.0), 0.0)

        // 필터 처리된 이미지를 파일로 저장
        val outputImageFile = File.createTempFile("filtered_image", ".jpg")

        Imgcodecs.imwrite(outputImageFile.absolutePath, filteredImageMat)

        // 필터 처리된 이미지 파일 반환
        //val filteredImageStream = outputImageFile.inputStream()

        //outputImageFile.absolutePath

    }



}