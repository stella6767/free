package freeapp.life.stella.api.service


import com.fasterxml.jackson.databind.ObjectMapper
import freeapp.life.stella.api.web.dto.Entry
import freeapp.life.stella.api.web.dto.EntryListDto
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors


@Service
class PublicApiService(
    private val githubClient: RestClient,
    private val mapper: ObjectMapper,
) {

    private val log = KotlinLogging.logger { }

//    @Value("\${github.accessToken}")
//    private val gitHubToken: String = ""

    private val publicApiRepository =
        ConcurrentHashMap<Int, Entry>()

    @PostConstruct
    fun init() {

        if (publicApiRepository.isEmpty()){
            saveAllByJsonFile()
        }
    }

    private fun saveAllByJsonFile() {

        val resource = ClassPathResource("static/json/resources.json")

        val json =
            BufferedReader(InputStreamReader(resource.inputStream)).use { reader ->
                reader.lines().collect(Collectors.joining("\n"))
            }
        val entryListDto =
            mapper.readValue(json, EntryListDto::class.java)

        entryListDto.entries.forEachIndexed { index, entry ->
            publicApiRepository[index + 1] = entry
        }
    }


    fun getEntriesByCategory(category: String): EntryListDto {

        val entries = publicApiRepository.values.filter {
            it.Category == category
        }
        val fieldNames =
            Entry::class.java.getDeclaredFields().map {
                it.isAccessible = true
                it.name
            }

        return EntryListDto(entries.size, fieldNames, entries)
    }

    fun getAllCategory(): List<String> {
        return publicApiRepository.values.map {
            it.Category
        }.distinct()
    }


//    private fun testOctokit() {
//
//        //https://raw.githubusercontent.com/marcelscruz/public-apis/main/db/categories.json
//
//
//        val body = githubClient
//            .get()
//            .uri("/repos/marcelscruz/public-apis/contents/db/resources.json")
//            //.accept(MediaType.APPLICATION_JSON)
//            .header("Authorization", "Bearer $gitHubToken")
//            .header("Accept", "application/vnd.github.v3+json")
//            .header("X-GitHub-Api-Version", "2022-11-28")
//            .retrieve()
//            .body(String::class.java)
//
//        println(body)
//    }




}
