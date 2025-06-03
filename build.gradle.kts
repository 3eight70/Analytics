plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jooq.jooq-codegen-gradle") version "3.20.4"
}

group = "pet.project"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

object Version {
    const val CLICK_HOUSE = "0.8.6"
    const val POSTGRES = "42.7.6"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    implementation("com.clickhouse:clickhouse-jdbc:${Version.CLICK_HOUSE}")
    implementation("org.postgresql:postgresql:${Version.POSTGRES}")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = System.getenv("POSTGRES_CONNECTION_STRING") ?: "jdbc:postgresql://localhost:5432/postgres"
            user = System.getenv("POSTGRES_USER") ?: "postgres"
            password = System.getenv("POSTGRES_PASSWORD") ?: "postgres"
        }

        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
            }

            generate {
                isKotlinSetterJvmNameAnnotationsOnIsPrefix = true
                isPojosAsKotlinDataClasses = true
                isKotlinNotNullPojoAttributes = true
                isKotlinNotNullRecordAttributes = true
                isKotlinNotNullInterfaceAttributes = true
                isKotlinDefaultedNullablePojoAttributes = true
                isKotlinDefaultedNullableRecordAttributes = true
            }

            target {
                packageName = "pet.project.analytics.jooq"
                directory = "build/generated-src/jooq/main"
            }

            strategy {
                name = "org.jooq.codegen.DefaultGeneratorStrategy"
            }
        }
    }
}

//TODO: Исправить прямое указание версии
buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.6")
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder = "paketobuildpacks/builder-jammy-base:latest"
}

tasks.named("compileKotlin") {
    dependsOn(tasks.filter { it.name.startsWith("jooqCodegen") }.toTypedArray())
}