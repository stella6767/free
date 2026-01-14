package freeapp.life.stella.api.web


import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.QrService
import freeapp.life.stella.api.web.dto.QrGeneratorType
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.view.FragmentsRendering

@RequestMapping("/qr")
@Controller
class QrController(
    private val qrService: QrService,
) {


    @GetMapping("/")
    fun index(
        model: Model
    ): String {

        return "page/qrGenerate"
    }


    @HxRequest
    @GetMapping("/qr/{type}")
    fun qrType(
        @PathVariable type: QrGeneratorType,
        model: Model,
    ): FragmentsRendering {

        model.addAttribute("type", type)

        return FragmentsRendering
            .with("component/qrMainInput")
            .fragment("component/qrCodeBtn")
            .build()
    }


    @HxRequest
    @PostMapping("/qrcode")
    fun generateQrCode(
        model: Model,
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestParam type: QrGeneratorType,
        @RequestParam("file") file: MultipartFile? = null,
        @RequestParam qrReqDto: HashMap<String, Any>,
    ): FragmentsRendering {



        val qrCode =
            qrService.generateStaticQRCodeByType(type, qrReqDto, file)

        println(file?.originalFilename)

        model.addAttribute("imageData", qrCode)
        model.addAttribute("isGenerated", true)


        return FragmentsRendering
            .with("component/qrImg")
            .fragment("component/qrCodeBtn")
            .build()
    }



}
