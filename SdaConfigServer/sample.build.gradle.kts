import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.noarg")
    kotlin("plugin.spring")
    id("java-library")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.gorylenko.gradle-git-properties")
    // id("org.sonarqube")
    id("jacoco")
}

java.sourceCompatibility = JavaVersion.VERSION_17

springBoot {
    buildInfo()
}

configurations {
    implementation.get().exclude(module = "spring-boot-starter-tomcat")
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = true
val jar: Jar by tasks
jar.enabled = false

dependencies {
    //implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // implementation("com.oracle.ojdbc:ojdbc10:$oracleJdbcVersion")

    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    // implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    // implementation("org.springframework.cloud:spring-cloud-starter-stream-rabbit")

    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    // testImplementation("com.h2database:h2")
}