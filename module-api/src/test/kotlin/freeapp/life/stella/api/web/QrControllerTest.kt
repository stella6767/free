package freeapp.life.stella.api.web

import com.fasterxml.jackson.databind.ObjectMapper
import gg.jte.TemplateEngine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class QrControllerTest(
    private val mockmvc: MockMvc,
    private val mapper: ObjectMapper,
) {

    @Test
    fun generateQrCode() {
        val dummyContent = ByteArray(200 * 1024 * 1024).apply {
            fill(0x41)
        }

        val mockMultipartFile = MockMultipartFile(
            "file",
            "dummy-100mb.jpg",
            "image/jpeg",
            dummyContent
        )

        mockmvc.perform(
            multipart("/qr/code")
                .file(mockMultipartFile)
                .param("type", "PDF")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.TEXT_HTML)
        )
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())

    }

}