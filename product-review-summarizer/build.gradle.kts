plugins {
	kotlin("jvm") version "2.3.21"
	kotlin("plugin.spring") version "2.3.21"
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.3.21"
	kotlin("plugin.serialization") version "2.0.21"
}

group = "com.currymeister"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}


dependencies {
// Spring Boot Starters & Persistence
	implementation("org.springframework.boot:spring-boot-h2console")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	// Kotlin Core & Serialization
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // Cleaned up (kept standard Jackson)
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

	// Database & Migrations
	implementation("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("com.h2database:h2")

	// Environment Variables
	implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")

	// LangChain4j (Managed uniformly by the BOM version 1.22.0)
	implementation(platform("dev.langchain4j:langchain4j-bom:1.22.0"))
	implementation("dev.langchain4j:langchain4j")
	implementation("dev.langchain4j:langchain4j-kotlin")
	implementation("dev.langchain4j:langchain4j-open-ai")
	implementation("dev.langchain4j:langchain4j-spring-boot-starter") // Version omitted so BOM manages it seamlessly

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
		javaParameters.set(true)
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
