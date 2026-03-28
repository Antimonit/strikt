package strikt.gradle

import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.KotlinterExtension

plugins {
  id("org.jetbrains.kotlin.jvm")
}

configure<JavaPluginExtension> {
  sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
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
apply(plugin = "org.jmailen.kotlinter")
configure<KotlinterExtension> {
  ignoreFailures = true
//  indentSize = 2
  reporters = arrayOf("html", "plain")
}

apply(plugin = "com.adarshr.test-logger")
configure<TestLoggerExtension> {
  theme = ThemeType.MOCHA_PARALLEL
  showSimpleNames = true
}
