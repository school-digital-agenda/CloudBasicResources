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

val bootJar: BootJar by tasks
val jar: Jar by tasks
bootJar.enabled = true
jar.enabled = false

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    testImplementation("org.springframework.security:spring-security-test")
}
