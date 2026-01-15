package freeapp.life.stella.api.web

import com.fasterxml.jackson.databind.ObjectMapper
import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.CloudUploaderService
import freeapp.life.stella.api.web.dto.DownloadEventDto
import freeapp.life.stella.api.web.dto.InitialUploadReqDto
import freeapp.life.stella.api.web.dto.PresignedPartRequestDto
import freeapp.life.stella.api.web.dto.PresignedPartResponseDto
import freeapp.life.stella.api.web.dto.S3BrowserRequestDto
import freeapp.life.stella.api.web.dto.S3ConnectionRequestDto
import freeapp.life.stella.api.web.dto.S3UploadAbortDto
import freeapp.life.stella.api.web.dto.S3UploadCompleteDto
import freeapp.life.stella.api.web.dto.S3UploadResultDto
import freeapp.life.stella.api.web.dto.UploadInitiateResponseDto

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRedirectView
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRefreshView
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.FragmentsRendering


@RequestMapping("/cloud")
@Controller
class CloudUploaderController(
    private val cloudUploaderService: CloudUploaderService,
    private val mapper: ObjectMapper,
) {

    @GetMapping("/upload")
    fun uploadPage(
        model: Model,
        htmxRequest: HtmxRequest,
        @RequestParam currentPath: String = "",
        @AuthenticationPrincipal principal: UserPrincipal,
    ): String {

        val s3Key =
            cloudUploaderService.findCloudKeyByUser(user = principal.user)
                ?: throw EntityNotFoundException("s3Key not found")

        model.addAttribute("currentPath", currentPath)
        model.addAttribute("bucket", s3Key.bucket)

        if (htmxRequest.isHtmxRequest) {
            return "components/s3/uploadForm"
        }

        return "page/upload"
    }


    @ResponseBody
    @PostMapping("/upload/initiate")
    fun initiateUpload(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody dto: InitialUploadReqDto
    ): UploadInitiateResponseDto {

        val s3Key =
            cloudUploaderService.findCloudKeyByUser(user = principal.user)
                ?: throw EntityNotFoundException("s3Key not found")

        val initiateResponseDto = cloudUploaderService.initiateUpload(
            s3Key.bucket,
            dto.targetObjectDir,
            dto.filename,
            dto.contentType,
            dto.fileSize,
        )

        return initiateResponseDto
    }

    /**
     * 부분 업로드를 위한 서명된 URL 발급 요청
     */

    @ResponseBody
    @PostMapping("/upload/part-url")
    fun getPresignedPartUrl(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody dto: PresignedPartRequestDto
    ): PresignedPartResponseDto {

        return PresignedPartResponseDto(
            dto.partNumber,
            cloudUploaderService.getPresignedPartUrl(principal.user, dto)
        )
    }

    /**
     * MultiPart Upload 완료 요청
     */

    @ResponseBody
    @PostMapping("/upload/complete")
    fun completeUpload(
        @RequestBody dto: S3UploadCompleteDto,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): S3UploadResultDto {

        return cloudUploaderService.completeUpload(principal.user, dto)
    }


    /**
     * 멀티파트 업로드 중지
     */

    @ResponseBody
    @PostMapping("/upload/abort")
    fun abortMultipartUpload(
        @RequestBody s3UploadAbortDto: S3UploadAbortDto,
        @AuthenticationPrincipal principal: UserPrincipal,
    ) {

        val s3Key =
            cloudUploaderService.findCloudKeyByUser(user = principal.user)
                ?: throw EntityNotFoundException("s3Key not found")

        cloudUploaderService.abortMultipartUpload(s3Key.bucket, s3UploadAbortDto)
    }


    @HxRequest
    @PostMapping("/connect")
    fun connect(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid s3ConnectionRequestDto: S3ConnectionRequestDto,
        model: Model
    ): HtmxRedirectView {

        cloudUploaderService.testConnection(s3ConnectionRequestDto)
        cloudUploaderService.saveCloudKey(principal.user, s3ConnectionRequestDto)

        return HtmxRedirectView("/s3/browser")
    }


    @HxRequest
    @PutMapping("/disconnect")
    fun disconnect(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): HtmxRefreshView {
        cloudUploaderService.disconnectCloudKeyByUser(principal.user)
        return HtmxRefreshView()
    }


    @GetMapping("/browser")
    fun s3Browser(
        model: Model,
        htmxRequest: HtmxRequest,
        dto: S3BrowserRequestDto,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): FragmentsRendering {

        val s3Key =
            cloudUploaderService.findCloudKeyByUser(user = principal.user)
                ?: throw EntityNotFoundException("s3Key not found")

        val objects =
            cloudUploaderService.getObjectsByCloudKey(s3Key, dto.prefix, dto.size, dto.continuationToken)

        val finalObjects =
            cloudUploaderService.buildBreadcrumbs(dto.prefix, objects.objects)

        model.addAttribute("bucket", s3Key.bucket)
        model.addAttribute("objects", finalObjects)
        model.addAttribute("size", dto.size)
        model.addAttribute("continuationToken", objects.continuationToken)
        model.addAttribute("currentPath", dto.prefix)
        model.addAttribute("isLast", objects.isLast)

        if (htmxRequest.isHtmxRequest) {
            //model.asMap()
            return FragmentsRendering
                .with("components/s3/objectList")
                .fragment("components/s3/s3BtnNavbar")
                .build()
        }

        return FragmentsRendering.with("page/s3Browser").build()
    }

    @HxRequest
    @GetMapping("/browser/rows")
    fun s3Rows(
        model: Model,
        htmxRequest: HtmxRequest,
        dto: S3BrowserRequestDto,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): String {

        val s3Key =
            cloudUploaderService.findCloudKeyByUser(user = principal.user)
                ?: throw EntityNotFoundException("s3Key not found")

        val objects =
            cloudUploaderService.getObjectsByCloudKey(
                s3Key, dto.prefix, dto.size, dto.continuationToken
            )

        model.addAttribute("objects", objects.objects)
        model.addAttribute("size", dto.size)
        model.addAttribute("continuationToken", objects.continuationToken)
        model.addAttribute("isLast", objects.isLast)
        model.addAttribute("currentPath", dto.prefix)


        return "components/s3/objectBody"
    }

    @GetMapping("/download")
    @ResponseBody
    fun download(
        @RequestParam fileKey: String,
        @AuthenticationPrincipal principal: UserPrincipal,
        htmxResponse: HtmxResponse,
    ): ResponseEntity<Void> {

        val s3Key =
            cloudUploaderService.findCloudKeyByUser(user = principal.user)
                ?: throw EntityNotFoundException("s3Key not found")

        val downloadDto =
            cloudUploaderService.getDownloadPresignedUrl(principal.user, s3Key.bucket)

        // JSON 생성: {"eventName":{"key":"value"}}
        val triggerJson =
            mapper.writeValueAsString(DownloadEventDto(downloadDto))

        htmxResponse.addTrigger(triggerJson)

        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }


}
