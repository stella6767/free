package freeapp.life.stella.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import freeapp.life.stella.api.config.security.UserPrincipal
import freeapp.life.stella.api.service.file.S3Service
import freeapp.life.stella.api.util.addNoFromFilename
import freeapp.life.stella.api.util.customDelimiter
import freeapp.life.stella.api.util.generateRandomNumberString
import freeapp.life.stella.api.web.dto.CallReqDto

import freeapp.life.stella.api.web.dto.QrGeneratorType

import freeapp.life.stella.api.web.dto.TextReqDto
import freeapp.life.stella.api.web.dto.VCardReqDto
import freeapp.life.stella.api.web.dto.WifiReqDto

import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.util.*


@Service
class QrService(
    private val mapper: ObjectMapper,
    private val s3Service: S3Service,
) {

    private val log = KotlinLogging.logger { }

    private val inValidCharacters =
        setOf('\\', '{', '^', '}', '%', '`', ']', '\"', '>', '[', '~', '<', '#', '|')

    private val folderName = "QR"


    fun generateStaticQRCodeByType(
        type: QrGeneratorType,
        qrReqDto: HashMap<String, Any>,
        file: MultipartFile?
    ): String {

        log.debug { file?.originalFilename }

        return when (type) {
            QrGeneratorType.LINK, QrGeneratorType.TEXT -> {
                val textReqDto =
                    mapper.convertValue(qrReqDto, TextReqDto::class.java)
                generateStaticQRCode(textReqDto.text)
            }

            QrGeneratorType.WIFI -> {
                val wifiDto =
                    mapper.convertValue(qrReqDto, WifiReqDto::class.java)
                val qrValue = "WIFI:T:${wifiDto.encryption};S:${wifiDto.ssid};P:${wifiDto.password};;"
                generateStaticQRCode(qrValue)
            }

            QrGeneratorType.VCARD -> {
                val vCardDto =
                    mapper.convertValue(qrReqDto, VCardReqDto::class.java)
                val vCard = """
                    BEGIN:VCARD
                    VERSION:3.0
                    FN:${vCardDto.firstName}  ${vCardDto.lastName}
                    TEL;TYPE=CELL:${vCardDto.phoneNumber}                   
                    END:VCARD
                """.trimIndent()
                generateStaticQRCode(vCard)
            }

            QrGeneratorType.TEL -> {
                val callDto =
                    mapper.convertValue(qrReqDto, CallReqDto::class.java)
                val qrValue = "tel:${callDto.countryCode}${callDto.phoneNumber}"
                println(qrValue)
                generateStaticQRCode(qrValue)
            }
            QrGeneratorType.PDF -> {
                generateDynamicQRCode(file ?: throw EntityNotFoundException("file is null"), folderName)
            }
        }

    }


    fun generateStaticQRCode(
        qrValue: String,
        width: Int = 300,
        height: Int = 300
    ): String {

        val qrCodeWriter = QRCodeWriter()

        val hintMap: MutableMap<EncodeHintType, Any> = HashMap()
        hintMap[EncodeHintType.MARGIN] = 0
        hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"

        val bitMatrix =
            qrCodeWriter.encode(qrValue, BarcodeFormat.QR_CODE, width, height, hintMap)

        val outputStream = ByteArrayOutputStream()

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)

        val base64Image =
            Base64.getEncoder().encodeToString(outputStream.toByteArray())

        return base64Image
    }


    fun generateDynamicQRCode(
        file: MultipartFile,
        folderName: String
    ): String {

        val dynamicUrl =
            s3Service.putObject(file, folderName)

        return generateStaticQRCode(dynamicUrl)
    }






}
