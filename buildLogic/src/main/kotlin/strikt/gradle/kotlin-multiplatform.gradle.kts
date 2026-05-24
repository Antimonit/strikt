package strikt.gradle

import org.gradle.api.JavaVersion

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("strikt.gradle.kotlinter")
  id("strikt.gradle.test-logger")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

kotlin {
  jvm {
    withJava()
    compilerOptions {
      commonJvmCompilerOptions()
    }
  }

  compilerOptions {
    commonCompilerOptions()
  }
}

commonTest()

val libs = the<VersionCatalogsExtension>().named("libs")

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(project.dependencies.platform(libs.findLibrary("kotlin-bom").get()))
      implementation(project.dependencies.platform(libs.findLibrary("kotlinx-coroutines-bom").get()))
    }
    jvmTest.dependencies {
      implementation(project.dependencies.platform(libs.findLibrary("junit-bom").get()))
      implementation(libs.findLibrary("junit-jupiter-api").get())
      runtimeOnly(libs.findLibrary("junit-jupiter-engine").get())
      runtimeOnly(libs.findLibrary("junit-platform-launcher").get())
    }
  }
}
