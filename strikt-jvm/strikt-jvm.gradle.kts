plugins {
  id("strikt.gradle.kotlin-jvm")
  alias(libs.plugins.published)
}

description = "Extensions for assertions and traversals on types from the Java standard library."

dependencies {
  api(project(":strikt-core"))

  testImplementation(libs.minutest)
}
