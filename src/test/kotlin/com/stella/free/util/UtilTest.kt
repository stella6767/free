package com.stella.free.util

import com.stella.free.core.blog.entity.Post
import com.stella.free.core.openapi.dto.Entry
import com.stella.free.core.openapi.service.PublicApiService
import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.global.config.TemplateConfiguration
import com.stella.free.global.config.WebClientConfig
import com.stella.free.global.util.TimeUtil
import com.stella.free.global.util.removeSpecialCharacters
import com.stella.free.web.component.common.ToastViewComponent
import de.tschuehly.spring.viewcomponent.core.IViewContext
import gg.jte.output.StringOutput
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import net.datafaker.transformations.Field.field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.aspectj.lang.ProceedingJoinPoint
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.LocalDateTime
import java.util.*
import java.util.function.Supplier
import kotlin.reflect.full.isSubclassOf
import kotlin.system.measureTimeMillis



@Disabled
class UtilTest {

    val faker = Faker(Locale("ko"))

    @Test
    fun largeDataFakerTest3(){

        val jenService = DummyDataJenService()

        val measuredTime = measureTimeMillis {
            val dummyPeople
                    = jenService.createDummyPersons(1000000, DummyDataJenService.AsyncType.SINGLE)
            //println(dummyPeople)
            println(dummyPeople.size)
        }

        println(measuredTime) //1645

    }

    @Test
    fun largeDataFakerTest2(){

        val jenService = DummyDataJenService()

        //https://jsonobject.tistory.com/606

//        val measuredTime = measureTimeMillis {
//            val dummyPeople
//            = jenService.createDummyPersonsByExecutorService(1000000)
//            //println(dummyPeople)
//            println(dummyPeople.size)
//        }
//
//        println(measuredTime) //1114
    }

    @Test
    fun largeDataFakerTest(){

        val jenService = DummyDataJenService()

        //https://jsonobject.tistory.com/606

        val dummyPeople =
            ArrayList<DummyDataJenService.DummyPerson>()

        val measuredTime = measureTimeMillis {
            runBlocking {
                val dummyPeople =
                    jenService.createDummyPersonsByCoroutine(100000, faker, dummyPeople)
                //println(dummyPeople)
                println(dummyPeople.size)
            }
        }

        println(measuredTime) //1114
    }

    @Test
    fun seleniumTest(){

        System.setProperty("webdriver.chrome.driver", "/Users/stella6767/chromedriver-mac-arm64/chromedriver")


        val url = "https://www.selenium.dev/documentation/webdriver/elements/finders/"

        val options = ChromeOptions()
        //페이지가 로드될 때까지 대기
        //Normal: 로드 이벤트 실행이 반환 될 때 까지 기다린다.
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL)
        val driver: WebDriver = ChromeDriver(options)

        driver.get(url)


    }


    @Test
    fun factorialTest(){

        val result = factorial(3)
        println(result)

    }


    fun factorial(n: Int): Int {

        if ( n == 1)  {
            return 1
        }

        return n * factorial(n - 1)
    }

    fun renderInject(joinPoint: ProceedingJoinPoint): Any {
        val returnValue = joinPoint.proceed()
        if (returnValue::class.isSubclassOf(IViewContext::class)) {
            returnValue as IViewContext
            val componentName = joinPoint.`this`.javaClass.simpleName.substringBefore("$$")
            val componentPackage = joinPoint.`this`.javaClass.`package`.name.replace(".", "/") + "/"
            returnValue.componentTemplate = "$componentPackage$componentName"
            return returnValue
        }
        return returnValue
    }



    @Test
    fun optionalTest(){

        val city = if (false) {
            Optional.ofNullable(City(1))
        } else Optional.ofNullable(null)

        val orElse = city.map { it.id }.orElse(2)

        println(orElse)


    }

    data class City(
        val id:Long
    )


    @Test
    fun outputTest(){

        val templateEngine =
            TemplateConfiguration().templateEngine()

        //val output = OwaspHtmlTemplateOutput(StringOutput())

        val output = StringOutput()
        val componentName =
            ToastViewComponent::class.java.simpleName?.substringBefore("$$")
        val componentPackage =
            ToastViewComponent::class.java.`package`.name.replace(".", "/") + "/"

        println(componentName)
        println(componentPackage)
        templateEngine.render("$componentPackage$componentName.jte", hashMapOf(), output)
        //templateEngine.render("com/stella/free/web/component/toast/ToastViewComponent.jte", hashMapOf(), output)
        println(output.toString())
    }

    @Test
    fun removeSpecialCharactersTest(){
        val input = "Hello! @#World 123!"
        //val result = StringUtil.removeSpecialCharacters(input)
        //System.out.println(result)

        val removeSpecialCharacters = input.removeSpecialCharacters()
        println(removeSpecialCharacters)
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