import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Path

buildscript {
    dependencies {
        classpath("gradle.plugin.com.ewerk.gradle.plugins:querydsl-plugin:1.0.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.7.21")
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.7.21")
    }
}

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("gg.jte.gradle") version "2.3.2"

    kotlin("kapt") version "1.7.21"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    kotlin("plugin.jpa") version "1.8.21"
}

val jteVersion = "2.3.2"

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val querydslVersion = "5.0.0"




allOpen {
    // Spring Boot 3.0.0
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")


//    annotation("javax.persistence.Entity")
//    annotation("javax.persistence.Embeddable")
//    annotation("javax.persistence.MappedSuperclass")
}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}



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

//    implementation("org.webjars:webjars-locator-core:0.52")
//    implementation("org.webjars.npm:htmx.org:1.8.4")

    // querydsl && jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("com.infobip:infobip-spring-data-jpa-querydsl-boot-starter:8.1.1")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")


    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")


//    implementation("com.querydsl:querydsl-core:${querydslVersion}")
//    implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
//
//    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa:jakarta")
//    //kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")
//    kapt("org.springframework.boot:spring-boot-configuration-processor")

// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")


    implementation("gg.jte:jte-spring-boot-starter-3:$jteVersion")
    // jte-kotlin is needed to compile kte templates
    implementation("gg.jte:jte-kotlin:$jteVersion")
    implementation("de.tschuehly:spring-view-component-jte:0.5.5-RC1")


    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")


    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.13.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")



    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")


    annotationProcessor("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
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
