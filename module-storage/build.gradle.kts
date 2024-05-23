
plugins {
    id("java")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

allOpen {
    // Spring Boot 3.0.0
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}


dependencies {

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.4.1")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.4.1")
    //sql query logging
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")


}