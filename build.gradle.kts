import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.7.10"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.noarg") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    // id("java-library")
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    id("org.springframework.boot") version "2.7.2"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.2.0"
    // id("org.flywaydb.flyway") version "8.5.11"
    id("com.gorylenko.gradle-git-properties") version "2.4.1"
    id("jacoco")
    id("com.palantir.docker") version "0.34.0" apply false
}

group = "br.com.sda.cloudbasic"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = false
}

extra["springCloudVersion"] = "2021.0.3"
extra["junitVersion"] = "5.8.2"
extra["kotlinLoggingVersion"] = "2.1.21"
extra["mockkVersion"] = "1.12.3"
extra["springMockkVersion"] = "3.1.1"
extra["generexVersion"] = "1.0.2"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "${rootProject.group}.${project.name}"
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

dependencies {
    implementation("org.postgresql:postgresql")
}
