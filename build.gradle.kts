import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"  apply false
    kotlin("plugin.noarg") version "1.6.21"  apply false
    kotlin("plugin.spring") version "1.6.21"  apply false
    id("java-library")
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.7.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1" apply false
    id("com.gorylenko.gradle-git-properties") version "2.4.0" apply false
}

group = "br.com.sda.cloudbasic"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

extra["springCloudVersion"] = "2021.0.3"
extra["junitVersion"] = "5.8.2"
extra["kotlinLoggingVersion"] = "2.1.21"
extra["mockkVersion"] = "1.12.3"
extra["springMockkVersion"] = "3.1.1"
extra["generexVersion"] = "1.0.2"

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib")

        implementation("org.springframework.cloud:spring-cloud-starter")
        implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

        implementation("io.github.microutils:kotlin-logging:${rootProject.property("kotlinLoggingVersion")}")

        testImplementation("io.mockk:mockk:${rootProject.property("mockkVersion")}")
        testImplementation("com.ninja-squad:springmockk:${rootProject.property("springMockkVersion")}")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
        testImplementation("com.github.mifmif:generex:${rootProject.property("generexVersion")}")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
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
}
