plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.mediatel.icc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

tasks {
	bootJar {
		archiveFileName.set("dbservice.jar")
	}
}

val jooqGenerator by configurations.creating

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.postgresql:postgresql:42.7.3")
	implementation("commons-codec:commons-codec:1.18.0")

	jooqGenerator("org.jooq:jooq:${property("jooqVersion")}")
	jooqGenerator("org.jooq:jooq-meta:${property("jooqVersion")}")
	jooqGenerator("org.jooq:jooq-codegen:${property("jooqVersion")}")
	jooqGenerator("org.postgresql:postgresql:42.7.3")

	compileOnly("org.projectlombok:lombok:1.18.36")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("generateJooq") {
	group = "jooq"
	description = "Generate JOOQ code from XML configuration"
	mainClass.set("org.jooq.codegen.GenerationTool")
	classpath = jooqGenerator
	args = listOf("${projectDir}/src/main/resources/jooq-config.xml")
}
