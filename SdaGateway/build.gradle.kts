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
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
//	implementation("org.springframework.cloud:spring-cloud-starter-gateway")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}
