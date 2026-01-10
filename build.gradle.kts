import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar


plugins {
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"

    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"  apply false
    kotlin("plugin.jpa") version "1.9.25"  apply false
    kotlin("plugin.allopen") version "1.9.25"  apply false
}



val projectGroup: String by project
val applicationVersion: String by project


java {
    sourceCompatibility = JavaVersion.VERSION_21
}



allprojects {
    group = projectGroup
    version = applicationVersion

    repositories {
        mavenCentral()
    }
}



subprojects {

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")


    dependencies {

        implementation("io.github.microutils:kotlin-logging:3.0.4")

        //json 직렬화
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta:2.15.2")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }


}


project(":module-storage") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}


