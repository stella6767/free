package freeapp.life.stella.storage.repository

import com.opencsv.CSVReader
import freeapp.life.stella.api.TodoHtmxApplication
import freeapp.life.stella.storage.config.JdslConfig
import freeapp.life.stella.storage.config.P6spyConfig
import freeapp.life.stella.storage.entity.SmokingSpot
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.Charset
import kotlin.collections.indexOf


//@Transactional
@ContextConfiguration(classes = [TodoHtmxApplication::class])
@Import(*[JdslConfig::class, P6spyConfig::class]) //, TestDataSource::class
@ActiveProfiles("local")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Any = h2
@DataJpaTest
class SmokingSpotRepositoryTest(
    private val smokingSpotRepository: SmokingSpotRepository,
) {


    private val NAME_KEYS = listOf(
        "시설명", "시설명(업소)", "건물명", "상호명", "시설 구분", "구분", "설치 주체"
    )

    private val ADDRESS_KEYS = listOf(
        "도로명주소",
        "소재지도로명주소",
        "주소",
        "지번주소",
        "설치위치",
        "설치 위치",
        "서울특별시 용산구 설치 위치"
    )





    private fun isLikelyAddress(value: String): Boolean {
        return Regex(
            "(서울|구|로|길|동|번지|\\d)"
        ).containsMatchIn(value)
    }


    @Test
    //@Rollback(false)
    fun test() {

        val spots = loadAllSmokingSpots(
            csvDir = File("src/test/resources/csv-utf8"),
            charset = Charsets.UTF_8   // UTF-16이면 여기만 바꿔도 됨
        )

        smokingSpotRepository.saveAll(spots)

        //spots.forEach { println(it) }
    }


    fun loadAllSmokingSpots(
        csvDir: File,
        charset: Charset
    ): List<SmokingSpot> {

        require(csvDir.isDirectory)

        return csvDir.listFiles { f ->
            f.extension.lowercase() == "csv"
        }?.flatMap { file ->
            try {
                parseCsvToSmokingSpots(file, charset)
            } catch (e: Exception) {
                println("⚠️ Failed to parse ${file.name}: ${e.message}")
                emptyList()
            }
        }
            ?: emptyList()
    }

    fun parseCsvToSmokingSpots(
        file: File,
        charset: Charset
    ): List<SmokingSpot> {

        CSVReader(InputStreamReader(file.inputStream(), charset)).use { reader ->
            val rows = reader.readAll()
            if (rows.isEmpty()) return emptyList()

            val header = rows.first()
            val dataRows = rows.drop(1)

            //println(header.joinToString(","))

            fun indexOfAny(keys: List<String>): Int? =
                header.indexOfFirst { it in keys }.takeIf { it >= 0 }

            //val nameIdx = indexOfAny(NAME_KEYS)

            val addressIdx = indexOfAny(ADDRESS_KEYS)


            return dataRows.mapNotNull { row ->
                val rawAddress = addressIdx
                    ?.let { row.getOrNull(it)?.trim() }
                    ?.takeIf { it.isNotBlank() && isLikelyAddress(it) }

                if (rawAddress == null) return@mapNotNull null


                val nameParts = NAME_KEYS.mapNotNull { key ->
                    val idx = header.indexOf(key)
                    if (idx >= 0) {
                        row.getOrNull(idx)?.trim()?.takeIf { it.isNotBlank() }
                    } else null
                }

                val name = nameParts
                    .distinct()
                    .joinToString(" ")
                    .takeIf { it.isNotBlank() }
                    ?: rawAddress   // 이름 없으면 주소로 대체


                val smokingSpot = SmokingSpot(
                    name = name,
                    address = normalizeAddress(rawAddress),
                )

                //println(smokingSpot)

                smokingSpot
            }
        }
    }




    fun extractName(row: Map<String, String>): String =
        row["시설명"]
            ?: row["건물명"]
            ?: row["상호명"]
            ?: row["시설명(업소)"]
            ?: row["설치위치"]
            ?: "흡연구역"

    fun normalizeAddress(
        rawAddress: String?,
        sido: String = "서울특별시",
        sigungu: String? = null
    ): String {
        if (rawAddress.isNullOrBlank()) return ""

        val clean = rawAddress
            .replace(Regex("\\(.*?\\)"), "") // 괄호 제거
            .replace(Regex("\\s+"), " ")
            .trim()

        val prefix = buildString {
            if (!sido.isNullOrBlank()) append(sido).append(" ")
            if (!sigungu.isNullOrBlank()) append(sigungu).append(" ")
        }

        return when {
            clean.startsWith("서울") -> clean
            else -> (prefix + clean).trim()
        }
    }

}