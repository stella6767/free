package com.stella.free.util

import com.stella.free.core.blog.entity.Post
import com.stella.free.core.openapi.dto.Entry
import com.stella.free.core.openapi.service.PublicApiService
import com.stella.free.core.scrap.dto.AsyncType
import com.stella.free.core.scrap.dto.ResultVO
import com.stella.free.core.scrap.service.DummyDataJenService
import com.stella.free.core.scrap.service.SeleniumBMPInterceptor
import com.stella.free.core.scrap.service.TestSeleniumService
import com.stella.free.core.scrap.service.VideoDownloaderUtil
import com.stella.free.global.config.TemplateConfiguration
import com.stella.free.global.config.WebClientConfig
import com.stella.free.global.util.TimeUtil
import com.stella.free.global.util.removeSpecialCharacters
import com.stella.free.web.component.todo.TodoViewComponent
import de.tschuehly.spring.viewcomponent.core.IViewContext
import gg.jte.output.StringOutput
import kotlinx.coroutines.*
import net.datafaker.Faker
import net.datafaker.transformations.Field.field
import net.datafaker.transformations.JavaObjectTransformer
import net.datafaker.transformations.Schema
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Supplier
import kotlin.concurrent.thread
import kotlin.reflect.full.isSubclassOf
import kotlin.system.measureTimeMillis


//@Disabled
class UtilTest {

    //val faker = Faker(Locale("ko"))


    @Test
    fun mp4Test(){

        val url = "https://google.com"

        val tsUrl = URI(url).toURL()
        val connection = tsUrl.openConnection() as HttpURLConnection

        connection.addRequestProperty("User-Agent", "Mozilla")
        connection.addRequestProperty("Referer", "https://google.com")
        connection.connect()

        connection.inputStream.use {

            val m3u8Bytes = IOUtils.toByteArray(it)
            //val downloadedByteLength = m3u8Bytes.size

            val file = File("/Users/stella6767/IdeaProjects/free/output/test.mp4")
            FileUtils.writeByteArrayToFile(file, m3u8Bytes)

        }
    }


    @Test
    fun resourcePathTest(){

        //val resource = this::class.java.getResource("/test.txt")
        //println(resource.path)

        //val path = ClassPathResource("static").url.path
        //println(path)

        val downloadDirectory =
            Paths.get(".").toAbsolutePath().toUri().normalize().rawPath + "output/"

        println(downloadDirectory)


        val seleniumService = TestSeleniumService()

        val url =
            "https://vod.inflearn.com/videos/a46b98e3-02ef-43c0-9947-8bd1d25a77a2/encrypted/1.m3u8"

        seleniumService.downloadTsByUrl(url)

        //println(OS.isFamilyUnix())
        //println(OS.isFamilyWindows())


//        val canonicalPath = File("/output/test.txt").canonicalPath
//
//        println(canonicalPath)


    }





    @Test
    fun seleniumBMPInterceptorTest(){

        val url =
            "https://www.munute.com/master/output/plan/U2FsdGVkX18vMpKBIjg+aE5C6jwlpmE89X94YBM3dI3ftdKZRE8VY7mF0gaNKw6t"

        val seleniumBMPInterceptor = SeleniumBMPInterceptor()
        val videoDownloaderUtil = VideoDownloaderUtil()

        val m3U8requestFiles =
            seleniumBMPInterceptor.retrieveM3U8requestFiles(url)

        println(m3U8requestFiles)

        val baseName = FilenameUtils.getBaseName(m3U8requestFiles.first())
        println(baseName)

    }



    @Test
    fun graphQlClientTest(){

//        val execute = graphQlClient
//            .document(userTagsQuery)
//            .operationName("UserTags")
//            .variable("username", username)
//            .execute()
//
//        val block = execute.block()
//        println(block.toString())

    }


    @Test
    fun threadTest(){

        val thread = thread {
            Thread.sleep(2000)
            println("doing something")
        }

        Thread.ofVirtual().start {
            println("virtual thread start!")
        }

        println("main thread ")
        thread.join()
    }



    @Test
    fun asyncTest() {
        val ints =
            mutableListOf<Int>(1, 2, 3, 4, 5)
        runBlocking {
            ints.map { prev ->
                CoroutineScope(Executors.newFixedThreadPool(1).asCoroutineDispatcher())
                    .async {
                        doSomeWithSuspend(
                            prev
                        )
                    }
            }.awaitAll().forEach { msg -> println(msg) }
        }
    }


    fun doSome(prev: Int): Int {
        Thread.sleep(1000)
        println(Thread.currentThread().name + " ::: $prev")
        return prev
    }

    @Test
    fun asyncTest2() {

        val executorService = Executors.newFixedThreadPool(1)

        val ints =
            mutableListOf<Int>(1, 2, 3, 4, 5)

        val callables = ints.map { prev ->
            CompletableFuture.supplyAsync(
                {doSome(prev)},
                executorService
            )
        }

        callables.map { it.join() }.forEach { println(it) }
    }



    suspend fun doSomeWithSuspend(prev: Int): Int {

        //delay(1000)
        Thread.sleep(1000)
        println(Thread.currentThread().name + " ::: $prev")

        return prev
    }

    @Test
    fun testThread() {
        println("Test with java thread ")

        repeat(10) {
            Thread(Runnable {
                println(" Number ${it}: ${Thread.currentThread().name}")
            }).start()
        }
        Thread.sleep(100)
    }


    @Test
    fun testCoroutine() {
        println("Coroutine test ")
        repeat(10) {
            GlobalScope.launch {
                println("Hi  Number  ${it}: ${Thread.currentThread().name}")
            }
        }
        Thread.sleep(100)
    }


    @Test
    fun testDelay() {
        println("Test to delay a coroutine: ")
        repeat(10) {
            GlobalScope.launch {
                println("Before  execution time $it: ${Thread.currentThread().name}")
                delay(10)
                println("After  execution time $it: ${Thread.currentThread().name}")
            }
        }
        Thread.sleep(200)
    }



    @Test
    fun encoderTest() {


        val uuid =
            UUID.randomUUID()

        val encPassword =
            BCryptPasswordEncoder().encode(uuid.toString())

        println(encPassword)

    }

    @Test
    fun largeDataFakerTest() {

        val jenService = DummyDataJenService()

        //https://jsonobject.tistory.com/606

        val measuredTime = measureTimeMillis {
            runBlocking {
                val dummyPeople =
                    jenService.createDummyPersons(100000, AsyncType.COROUTINE)
                //println(dummyPeople)
                println(dummyPeople.size)
            }
        }

        println(measuredTime) //1114
    }


    val scope =
        CoroutineScope(Executors.newFixedThreadPool(1).asCoroutineDispatcher())




    suspend fun doSomethingWithSuspend(): String {
        //delay(3000)
        Thread.sleep(3000)

        return "ok"
    }

    fun doSomething(): String {

        Thread.sleep(3000)

        return "ok"
    }

    //@Test
    fun seleniumTest() {
//
//        System.setProperty("webdriver.chrome.driver", "/Users/stella6767/chromedriver-mac-arm64/chromedriver")
//
//
//        val url = "https://www.selenium.dev/documentation/webdriver/elements/finders/"
//
//        val options = ChromeOptions()
//        //페이지가 로드될 때까지 대기
//        //Normal: 로드 이벤트 실행이 반환 될 때 까지 기다린다.
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL)
//        val driver: WebDriver = ChromeDriver(options)
//
//        driver.get(url)


    }


    @Test
    fun factorialTest() {

        val result = factorial(3)
        println(result)

    }


    fun factorial(n: Int): Int {

        if (n == 1) {
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
    fun optionalTest() {

        val city = if (false) {
            Optional.ofNullable(City(1))
        } else Optional.ofNullable(null)

        val orElse = city.map { it.id }.orElse(2)

        println(orElse)


    }

    data class City(
        val id: Long
    )


    @Test
    fun outputTest() {

        val templateEngine =
            TemplateConfiguration().templateEngine()

        //val output = OwaspHtmlTemplateOutput(StringOutput())

        val output = StringOutput()
        val componentName =
            TodoViewComponent::class.java.simpleName?.substringBefore("$$")
        val componentPackage =
            TodoViewComponent::class.java.`package`.name.replace(".", "/") + "/"

        println(componentName)
        println(componentPackage)
        templateEngine.render("$componentPackage$componentName.jte", hashMapOf(), output)
        //templateEngine.render("com/stella/free/web/component/toast/ToastViewComponent.jte", hashMapOf(), output)
        println(output.toString())
    }

    @Test
    fun removeSpecialCharactersTest() {
        val input = "Hello! @#World 123!"
        //val result = StringUtil.removeSpecialCharacters(input)
        //System.out.println(result)

        val removeSpecialCharacters = input.removeSpecialCharacters()
        println(removeSpecialCharacters)
    }

    @Test
    fun getNamesUsingReflectionTest() {

        val fieldNames =
            Entry::class.java.getDeclaredFields().map {
                it.isAccessible = true
                it.name
            }

        println(fieldNames)

    }


    @Test
    fun openApiTest() {

        val apiClient = WebClientConfig().publicApiClient()

        PublicApiService(apiClient).getAllCategory()

    }

    @Test
    fun timeUtilTest() {

        val timeToString =
            TimeUtil.localDateTimeToString(LocalDateTime.now(), "YYYY-MM-dd E HH:mm")

        println(timeToString)
    }


    @Test
    fun dataFakerTest() {

        val faker = Faker(Locale("ko"))
        val jTransformer = JavaObjectTransformer()


//        println(faker.cultureSeries().planets())
//        println(faker.famousLastWords().lastWords())
//        println(faker.shakespeare().asYouLikeItQuote())
//        println(faker.verb().simplePresent())

        val schema: Schema<Any, Any> = Schema.of(
            field("title", Supplier { faker.book().title() }),
            field("content", Supplier { faker.famousLastWords().lastWords() }),
            field("thumbnail", Supplier { faker.internet().image() })
        )

        val post =
            jTransformer.apply(Post::class.java, schema) as Post

        println(post)
    }


    @Test
    fun jasyptTest() {

        val key = "asdasd"

        val gitHubSecret = "9404bfe223cd1d7c01c1"
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


        val encryptGithub = jasyptEncrypt(key, gitHubSecret)
        val encryptGoogle = jasyptEncrypt(key, googleSecret)
        val encryptFacebook = jasyptEncrypt(key, facebookSecret)


        val decryt =
            jasyptDecryt("1234", "JyDEzmBcjaToslpqTnVKxvopkhw5xSnURceG0ggywM9sV23SRstMhMKVFn/ziedt1+HaZ0Lo8LQ=")


        println("!!!!!!!!")
        println(decryt)


        System.out.println("encryptGithub : " + encryptGithub)
        System.out.println("encryptGoogle" + encryptGoogle)
        System.out.println("encryptFacebook" + encryptFacebook)


    }

    private fun jasyptEncrypt(key: String, input: String): String? {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.encrypt(input)
    }

    private fun jasyptDecryt(key: String, input: String): String? {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(key)
        return encryptor.decrypt(input)
    }


}