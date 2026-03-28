package strikt.gradle

import com.adarshr.gradle.testlogger.theme.ThemeType
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jmailen.kotlinter")
  id("com.adarshr.test-logger")
}

configure<JavaPluginExtension> {
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

// Lint Kotlin code
kotlinter {
  ignoreFailures = true
//  indentSize = 2
  reporters = arrayOf("html", "plain")
}

testlogger {
  theme = ThemeType.MOCHA_PARALLEL
  showSimpleNames = true
}
