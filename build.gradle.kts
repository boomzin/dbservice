plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"

}

group = "ru.mediatel.icc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks {
	bootJar {
		archiveFileName.set("dbservice.jar")
	}
}

configurations {
	create("jooqGenerator")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.postgresql:postgresql:42.7.3")

	"jooqGenerator"("org.jooq:jooq-codegen:3.19.24")
	"jooqGenerator"("org.postgresql:postgresql:42.7.3")

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
	classpath = configurations["jooqGenerator"]
	args = listOf("${projectDir}/src/main/resources/jooq-config.xml")
}
