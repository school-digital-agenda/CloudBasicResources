import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

ext["newRelicApiVersion"] = "7.5.0"
ext["kotlinLoggingVersion"] = "2.1.21"
ext["oracleJdbcVersion"] = "19.3.0.0"
ext["springCloudVersion"] = "2021.0.1"
ext["mockkVersion"] = "1.12.3"
ext["springMockkVersion"] = "3.1.1"
ext["generexVersion"] = "1.0.2"

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"  apply false
    kotlin("plugin.noarg") version "1.6.21"  apply false
    kotlin("plugin.spring") version "1.6.21"  apply false
    id("java-library")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1" apply false
    id("org.springframework.boot") version "2.6.7" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.gorylenko.gradle-git-properties") version "2.4.0" apply false
    id("jacoco")
    // id("org.sonarqube") version "3.3" apply false
    // id("org.flywaydb.flyway") version "8.5.4"
}

group = "br.com.sda"

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
//        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
//        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

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
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${rootProject.property("springCloudVersion")}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    /************************
     * JaCoCo Configuration *
     ************************/
    val exclusions = listOf(
        "**/*DTO.*",
        "**/*VO.*",
        "**/*Request*.*",
        "**/*Response*.*",
        "**/annotation/**",
        "**/exception/**",
        "**/config/**",
        "**/configuration/**",
    )

    afterEvaluate {
        jacoco {
            toolVersion = "0.8.7"
        }

        tasks.jacocoTestReport {
            reports {
                html.required.set(true)
                xml.required.set(true)
                csv.required.set(false)
            }
            classDirectories.setFrom(
                sourceSets.main.get().output.asFileTree.matching {
                    exclude(exclusions)
                }
            )
            dependsOn(tasks.test)
        }

        tasks.jacocoTestCoverageVerification {
            violationRules {
                rule {
                    element = "BUNDLE"
                    limit {
                        counter = "INSTRUCTION"
                        value = "COVEREDRATIO"
                        minimum = "0.8".toBigDecimal()
                    }
                }
            }
            classDirectories.setFrom(
                sourceSets.main.get().output.asFileTree.matching {
                    exclude(exclusions)
                }
            )
            dependsOn(tasks.jacocoTestReport)
        }

        tasks.withType<Test> {
            testLogging.showStandardStreams = true
            useJUnitPlatform()
            finalizedBy(tasks.jacocoTestCoverageVerification)
        }

    }
}
