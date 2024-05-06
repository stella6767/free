import gg.jte.gradle.GenerateJteTask
import org.jetbrains.kotlin.cli.jvm.main
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Path



plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("gg.jte.gradle") version "2.3.2"

    kotlin("kapt") version "1.9.22"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    idea
}


group = "com.stella"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


ext["selenium.version"] = "4.20.0"

val querydslVersion = "5.0.0"
val jteVersion = "3.1.10"


allOpen {
    // Spring Boot 3.0.0
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

//sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
//    kotlin.srcDir("$buildDir/generated/source/kapt/main")
//}



jte {
    //sourceDirectory.set("src/main/kotlin")
    sourceDirectory.set(Path.of("src","main","kotlin"))
    //precompile()
    generate()
}


repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {


    //aws
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")


    implementation("org.dhatim:fastexcel:0.16.4")
    implementation("org.dhatim:fastexcel-reader:0.16.4")


    // mac silicon only
    // https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/SystemUtils.java#L1173
    val isMacOS: Boolean = System.getProperty("os.name").startsWith("Mac OS X")
    val architecture = System.getProperty("os.arch").toLowerCase()
    if (isMacOS && architecture == "aarch64") {
        developmentOnly("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
    }
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    //https://kotlinworld.com/381
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")


    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // kotlin jdsl
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.3.1")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.3.1")


    // 더미데이터 셍상
    implementation("net.datafaker:datafaker:2.0.1")

    // html to 마크다운
    implementation("com.vladsch.flexmark:flexmark-html2md-converter:0.64.0")
    implementation("org.commonmark:commonmark:0.22.0")


    // 파싱, cralwer
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("org.seleniumhq.selenium:selenium-java:4.20.0")

    //ffmpeg wrapper
    //implementation("net.bramp.ffmpeg:ffmpeg:0.7.0")
    //implementation("org.seleniumhq.selenium:selenium-devtools-v124:4.20.0")
//    implementation("com.browserup:browserup-proxy-core:2.1.2")
//    implementation("io.github.bonigarcia:webdrivermanager:5.8.0")


    //sql query logging
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
    //환경변수 암호화
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    //common utilities
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-io:commons-io:2.16.1")

    // jte template engine
    implementation("gg.jte:jte-spring-boot-starter-3:$jteVersion")
    // jte-kotlin is needed to compile kte templates
    implementation("gg.jte:jte-kotlin:$jteVersion")
    //spring-view-component
    implementation("de.tschuehly:spring-view-component-jte:0.5.5-RC1")


    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    //json 직렬화
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta:2.15.2")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
// https://mvnrepository.com/artifact/org.awaitility/awaitility
    testImplementation("org.awaitility:awaitility:4.2.0")
    testImplementation("org.springframework:spring-webflux")
    testImplementation("org.springframework.graphql:spring-graphql-test")

}


tasks.jar {
    enabled = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


//tasks.withType<GenerateJteTask> {
//    // 'kaptGenerateStubsKotlin'이 이 태스크를 완료된 후에 실행되도록 설정
//
//}

tasks.withType<KaptGenerateStubsTask> {
    // 'kaptGenerateStubsKotlin'이 이 태스크를 완료된 후에 실행되도록 설정
    dependsOn(tasks.generateJte)
}






sourceSets {
    test {
        resources {
            srcDir("src/test/kotlin")
            exclude("**/*.kt")
        }
    }
    main {
        resources {
            srcDir("src/main/kotlin")
            exclude("**/*.kt")
        }
    }
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}