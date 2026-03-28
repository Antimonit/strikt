package strikt.gradle

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("strikt.gradle.kotlinter")
  id("strikt.gradle.test-logger")
}

kotlin {
  compilerOptions {
    commonCompilerOptions()
  }
}

commonTest()
