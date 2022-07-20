import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.noarg")
    kotlin("plugin.jpa")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.gorylenko.gradle-git-properties")
    id("jacoco")
    id("com.palantir.docker")
}

group = "${rootProject.group}.${project.name}"
version = rootProject.version
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

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = false
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
// 	implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
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

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

/************************
 * JaCoCo Configuration *
 ************************/
jacoco {
    toolVersion = "0.8.7"
}

val exclusions = listOf(
    "**/config/**",
    "**/exception/**",
    "**/SdaGatewayApplication.kt"
)

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

tasks.test {
    configure<JacocoTaskExtension> {
        excludes = emptyList()
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "BUNDLE"
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.1".toBigDecimal()
            }
        }

        rule {
            element = "CLASS"
            includes = listOf("org.gradle.*")
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.1".toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(exclusions)
        }
    )
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
}

val ecrRegistry = System.getenv("ECR_REGISTRY") ?: project.group
val ecrRepository = System.getenv("ECR_REPOSITORY") ?: "${project.name}"

docker {
    name = "$ecrRegistry/$ecrRepository:${project.version}"
    files("${project.buildDir}/libs/${project.name}-${project.version}.jar")
}
