//package freeapp.life.stella.api.config
//
//import gg.jte.CodeResolver
//import gg.jte.ContentType
//import gg.jte.TemplateEngine
//import gg.jte.TemplateNotFoundException
//import gg.jte.resolve.DirectoryCodeResolver
//import gg.jte.springframework.boot.autoconfigure.JteProperties
//import gg.jte.springframework.boot.autoconfigure.JteViewResolver
//import mu.KLogging
//import mu.KotlinLogging
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.Ordered
//import org.springframework.core.annotation.Order
//import org.springframework.web.context.request.RequestContextHolder
//import org.springframework.web.context.request.ServletRequestAttributes
//import org.springframework.web.servlet.View
//import org.springframework.web.servlet.ViewResolver
//import java.io.IOException
//import java.io.UncheckedIOException
//import java.nio.file.FileVisitResult
//import java.nio.file.Files
//import java.nio.file.Path
//import java.nio.file.Paths
//import java.nio.file.SimpleFileVisitor
//import java.nio.file.attribute.BasicFileAttributes
//import java.util.Locale
//
//@Configuration
//class JteConfiguration(
//    var jteProperties: JteProperties,
//) {
//
//    companion object : KLogging() { // KLogging 사용
//        private val JTE_SOURCE_ROOT: Path = Paths.get("module-api","src", "main", "jte")
//    }
//
//    @Bean
//    fun jteTemplateEngine(): TemplateEngine {
//        logger.info { "JTE 템플릿 소스 루트 (설정값): ${JTE_SOURCE_ROOT}" }
//        logger.info { "JTE 템플릿 소스 루트 (절대 경로): ${JTE_SOURCE_ROOT.toAbsolutePath()}" }
//
//        if (!Files.exists(JTE_SOURCE_ROOT) || !Files.isDirectory(JTE_SOURCE_ROOT)) {
//            logger.error { "!!! JTE 템플릿 소스 디렉토리를 찾을 수 없습니다: ${JTE_SOURCE_ROOT.toAbsolutePath()}" }
//            logger.error { "!!! 현재 작업 디렉토리: ${Paths.get(".").toAbsolutePath().normalize()}" }
//            // 개발 중에는 오류를 발생시켜 문제를 인지하도록 하는 것이 좋습니다.
//            throw IllegalStateException("JTE template source directory not found: ${JTE_SOURCE_ROOT.toAbsolutePath()}")
//        }
//        logger.info { "JTE 템플릿 소스 디렉토리 확인됨: ${JTE_SOURCE_ROOT.toAbsolutePath()}" }
//
//
//        val codeResolver: CodeResolver = DirectoryCodeResolver(JTE_SOURCE_ROOT)
//        val classDirectory: Path = Paths.get("build", "jte-classes-dynamic") // 동적 컴파일용 클래스 저장소
//        try {
//            Files.createDirectories(classDirectory)
//        } catch (e: IOException) {
//            throw UncheckedIOException("JTE 동적 컴파일 클래스 디렉토리 생성 실패: $classDirectory", e)
//        }
//
//        // 개발 모드에서는 동적 컴파일을 사용한다고 가정
//        // gg.jte.precompiled=true 또는 관련 프로퍼티를 확인하여 분기할 수 있습니다.
//        val templateEngine = TemplateEngine.create(
//            codeResolver,
//            classDirectory,
//            ContentType.Html,
//            javaClass.classLoader
//        )
//        // templateEngine.setDevelopmentMode(true); // JTE 자체 개발 모드 활성화 (더 많은 변경 감지 및 빠른 재컴파일)
//        logger.info { "JTE TemplateEngine (동적 컴파일 모드) 생성 완료." }
//        return templateEngine
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE) // 다른 뷰 리졸버보다 우선권을 갖도록 설정
//    fun viewResolver(templateEngine: TemplateEngine): ViewResolver {
//        //val resolver = jteViewResolver(templateEngine)
//
//        return  JteViewResolver(templateEngine, jteProperties).apply {
//
//        }
//    }
//
//}
