

plugins {
    id("java")
    kotlin("kapt")
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
    implementation("com.microsoft.playwright:playwright:1.44.0")


    //common utilities
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-io:commons-io:2.16.1")


    implementation("org.springframework.ai:spring-ai-ollama-spring-boot-starter")



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



tasks.jar {
    enabled = false
}
