package com.stella.free.core.scrap.api

import com.stella.free.core.scrap.dto.AsyncType
import com.stella.free.core.scrap.dto.UrlReqDto
import com.stella.free.core.scrap.dto.VelogCrawlerReqDto
import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.core.scrap.service.TSDownloaderService
import com.stella.free.core.scrap.service.VelogCrawler
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.UrlResource
import org.springframework.http.CacheControl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Paths


@Controller
class ScrapController(
    private val dummyDataJenService: DummyDataJenService,
    private val velogCrawler: VelogCrawler,
    private val TSDownloaderService: TSDownloaderService,
) {


    @GetMapping("/velog")
    @ResponseBody
    fun velogApiTest(
        @Valid velogCrawlerReqDto: VelogCrawlerReqDto,
        response: HttpServletResponse
    ) {
        val headers = HttpHeaders()
        headers.add(CONTENT_DISPOSITION, "attachment; filename=files.zip")
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip")
        velogCrawler.parseAndDownloadAsZip(velogCrawlerReqDto.username, response.outputStream)
    }


    @GetMapping("/dummy/{size}")
    fun dummyGen(@PathVariable size:Int,
                 @RequestParam type: AsyncType
    ): String {
        dummyDataJenService.createDummyPersons(size, type)
        return ""
    }





    @PostMapping("/ts/url")
    @ResponseBody
    fun downloadTsByUrl(urlReqDto: UrlReqDto): ResponseEntity<UrlResource> {


        throw IllegalArgumentException("I removed the feature due to lack of support for the Chrome browser on ARM architecture.")

        val downloadedFile =
            TSDownloaderService.downloadTsByUrl(urlReqDto.url)

        val resource = UrlResource(Paths.get(downloadedFile.canonicalPath).toUri())

        //인코딩 이슈
        val sanitizedName =
            URLEncoder.encode(
                FilenameUtils.getName(downloadedFile.canonicalPath),
                StandardCharsets.UTF_8.toString()
            )

        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(resource.contentLength())
            .cacheControl(CacheControl.noCache())
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${sanitizedName}")
            .header("tmpName", URLEncoder.encode(downloadedFile.canonicalPath, StandardCharsets.UTF_8.toString()))
            .body(resource)
    }






}