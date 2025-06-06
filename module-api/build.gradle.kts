

plugins {
    id("java")
    kotlin("kapt")
    id("gg.jte.gradle") version "3.1.12"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

extra["springAiVersion"] = "1.0.0-M5"


dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

dependencies {

    implementation(project(":module-storage"))

    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-html
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")

    //implementation("org.dhatim:fastexcel:0.16.4")
    //implementation("org.dhatim:fastexcel-reader:0.16.4")


    //aws
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")


    // html to 마크다운
    implementation("com.vladsch.flexmark:flexmark-html2md-converter:0.64.0")
    implementation("org.commonmark:commonmark:0.22.0")

    // 파싱, cralwer
    //implementation("org.jsoup:jsoup:1.16.1")
    implementation("com.mohamedrejeb.ksoup:ksoup-html:0.3.1")

    //common utilities
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-io:commons-io:2.16.1")


    implementation("gg.jte:jte-spring-boot-starter-3:3.1.12")
    implementation("gg.jte:jte:3.1.12")
    implementation("gg.jte:jte-kotlin:3.1.12")
    testImplementation("gg.jte:jte-kotlin:3.1.12")

    //implementation("net.sf.trove4j:trove4j:3.0.3")


    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
//    implementation("org.springframework.boot:spring-boot-starter-cache")
//    implementation("com.github.ben-manes.caffeine:caffeine")


    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.testng:testng:7.1.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework:spring-webflux")

}

jte {
    generate()
    binaryStaticContent = true
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs::class.java).configureEach {
    // kaptGenerateStubsKotlin 태스크가 generateJte 태스크 이후에 실행되도록 설정
    dependsOn(tasks.named("generateJte"))

    // 만약 generateJte의 출력물을 kaptGenerateStubsKotlin의 소스로 명시적으로 추가해야 한다면
    // (일반적으로 JTE 플러그인이 자동으로 소스셋에 추가하지만, 문제가 지속될 경우 고려)
    // 예를 들어, JTE가 Java 파일을 생성하고 이를 Kotlin 스텁 생성 시 참조해야 하는 경우:
    // source(tasks.named("generateJte").map { it.outputs.files })
}


tasks.jar {
    enabled = false
}
