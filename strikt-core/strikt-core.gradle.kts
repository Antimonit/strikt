plugins {
  id("strikt.gradle.kotlin-multiplatform")
  alias(libs.plugins.published)
}

description = "The core API for Strikt."

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
    }
    jvmMain.dependencies {
      api(libs.opentest4j)
    }
    jvmTest.dependencies {
      implementation(libs.minutest)
      implementation(libs.kotlinx.datetime)
    }
  }
}
