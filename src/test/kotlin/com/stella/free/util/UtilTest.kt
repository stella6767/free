package com.stella.free.util

import com.stella.free.entity.Post
import net.datafaker.Faker
import net.datafaker.transformations.Field.field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Test
import java.util.*
import java.util.function.Supplier


class UtilTest {


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