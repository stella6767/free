package com.stella.free.util

import com.stella.free.core.blog.entity.Post
import com.stella.free.core.openapi.dto.Entry
import com.stella.free.core.openapi.service.PublicApiService
import com.stella.free.global.config.WebClientConfig
import com.stella.free.global.util.StringUtil
import com.stella.free.global.util.TimeUtil
import net.datafaker.Faker
import net.datafaker.transformations.Field.field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*
import java.util.function.Supplier


class UtilTest {



    @Test
    fun removeSpecialCharactersTest(){
        val input = "Hello! @#World 123!"
        val result = StringUtil.removeSpecialCharacters(input)
        System.out.println(result)
    }

    @Test
    fun getNamesUsingReflectionTest(){

        val fieldNames =
            Entry::class.java.getDeclaredFields().map {
                it.isAccessible = true
                it.name
            }

        println(fieldNames)

    }


    @Test
    fun openApiTest(){

        val apiClient = WebClientConfig().publicApiClient()

        PublicApiService(apiClient).getAllCategory()

    }

    @Test
    fun timeUtilTest(){

        val timeToString =
            TimeUtil.localDateTimeToString(LocalDateTime.now(), "YYYY-MM-dd E HH:mm")

        println(timeToString)
    }


    @Test
    fun dataFakerTest(){

        val faker = Faker(Locale("ko"))
        val jTransformer = JavaObjectTransformer()


//        println(faker.cultureSeries().planets())
//        println(faker.famousLastWords().lastWords())
//        println(faker.shakespeare().asYouLikeItQuote())
//        println(faker.verb().simplePresent())

        val schema: Schema<Any, Any> = Schema.of(
            field("title", Supplier { faker.book().title() }),
            field("content", Supplier { faker.famousLastWords().lastWords() }),
            field("thumbnail", Supplier { faker.internet().image() }))

        val post =
            jTransformer.apply(Post::class.java, schema) as Post

        println(post)
    }


    @Test
    fun jasyptTest() {

        val key = "asdasd"

        val gitHubSecret = "asdasd"
        val googleSecret = "asdasd"
        val facebookSecret = "asdasd"


        val supaBaseSecret = "kmp0WGpjMssxFZI3"

        /**
         * https://woawqxzinsnwsvghwanp.supabase.co
         * 5uX/HsTm0vB10jg8bZ1X5cDff8cHPver12oPVD5+bXRjjcSWC/UXerGyq8U4REv1t8hj4V2lNIA+C0mnt54MUg==
         *
         * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndvYXdxeHppbnNud3N2Z2h3YW5wIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTAxOTExNDcsImV4cCI6MjAwNTc2NzE0N30.Rxs3jeXc0D7A8icqP6KYWXs-roF8WT6H6CykiNSeuxI
         * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndvYXdxeHppbnNud3N2Z2h3YW5wIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5MDE5MTE0NywiZXhwIjoyMDA1NzY3MTQ3fQ.Tnt1tP0cn2klbdjEFdBE5uOM7i3OISUT-n-kl1wYIPo
         *
         * db.woawqxzinsnwsvghwanp.supabase.co
         * 5432
         * postgres
         * postgres://postgres:[YOUR-PASSWORD]@db.woawqxzinsnwsvghwanp.supabase.co:6543/postgres
         */


        val encryptGithub = jasyptEncrypt(key,gitHubSecret)
        val encryptGoogle = jasyptEncrypt(key,googleSecret)
        val encryptFacebook = jasyptEncrypt(key,facebookSecret)




        System.out.println("encryptGithub : " + encryptGithub)
        System.out.println("encryptGoogle" + encryptGoogle)
        System.out.println("encryptFacebook" + encryptFacebook)


    }

    private fun jasyptEncrypt(key:String, input: String): String? {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.encrypt(input)
    }

    private fun jasyptDecryt(key:String, input: String): String? {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.decrypt(input)
    }


}