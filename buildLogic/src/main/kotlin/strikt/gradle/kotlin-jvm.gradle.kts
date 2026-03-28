package strikt.gradle

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
    commonCompilerOptions()
    jvmTarget.set(JvmTarget.JVM_17)
    javaParameters = true
  }
}

commonTest()

val libs = the<VersionCatalogsExtension>().named("libs")

dependencies {
  "implementation"(platform(libs.findLibrary("kotlin-bom").get()))
  "implementation"(platform(libs.findLibrary("kotlinx-coroutines-bom").get()))

  "testImplementation"(platform(libs.findLibrary("junit-bom").get()))
  "testImplementation"(libs.findLibrary("junit-jupiter-api").get())
  "testRuntimeOnly"(libs.findLibrary("junit-jupiter-engine").get())
}
