package strikt.gradle

import org.gradle.api.JavaVersion
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("strikt.gradle.kotlinter")
  id("strikt.gradle.test-logger")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

kotlin {
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_17)
    languageVersion.set(KotlinVersion.KOTLIN_2_0)
    javaParameters = true
    freeCompilerArgs = listOf("-Xjvm-default=all")
    allWarningsAsErrors = true
  }
}

val libs = the<VersionCatalogsExtension>().named("libs")

dependencies {
  "implementation"(platform(libs.findLibrary("kotlin-bom").get()))
  "implementation"(platform(libs.findLibrary("kotlinx-coroutines-bom").get()))

  "testImplementation"(platform(libs.findLibrary("junit-bom").get()))
  "testImplementation"(libs.findLibrary("junit-jupiter-api").get())
  "testRuntimeOnly"(libs.findLibrary("junit-jupiter-engine").get())
}

// Test with JUnit 5
tasks.withType<Test> {
  systemProperty("junit.jupiter.execution.parallel.enabled", "false")
  useJUnitPlatform {
    includeEngines("junit-jupiter", "failgood")
  }
}
