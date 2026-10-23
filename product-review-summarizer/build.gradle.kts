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
	implementation("org.springframework.boot:spring-boot-h2console")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("tools.jackson.module:jackson-module-kotlin")
	implementation("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")// 1. LangChain4j Bill of Materials (BOM) to align all dependency versions
	implementation(platform("dev.langchain4j:langchain4j-bom:1.22.0"))

	// 2. Core LangChain4j library & Kotlin extensions (coroutines, type-safe builders)
	implementation("dev.langchain4j:langchain4j")
	implementation("dev.langchain4j:langchain4j-kotlin")

	// 3. Choose your LLM provider integration (e.g., OpenAI)
	implementation("dev.langchain4j:langchain4j-open-ai")

	// 4. Kotlin Serialization / Jackson (Recommended if using data classes or structured outputs)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// 5. Coroutines (If you plan to leverage async/suspend features from langchain4j-kotlin)
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

	implementation("dev.langchain4j:langchain4j-spring-boot-starter:1.0.0-beta1")

	// 6. Logging (SLF4J implementation required to see internal logs)
	implementation("org.slf4j:slf4j-simple:2.0.16")
	implementation("io.github.cdimascio:dotenv-kotlin:6.5.1")
	runtimeOnly("com.h2database:h2")
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
