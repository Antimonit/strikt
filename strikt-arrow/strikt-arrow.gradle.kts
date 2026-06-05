plugins {
  id("strikt.gradle.kotlin-jvm")
  alias(libs.plugins.published)
}

description = "Extensions for assertions and traversals on types from the Arrow functional programming library."

dependencies {
  api(project(":strikt-core"))

  compileOnly(libs.arrow)
  testImplementation(libs.arrow)

  testImplementation(libs.minutest)
}

dokka {
  dokkaSourceSets.configureEach {
    externalDocumentationLinks.register("arrow-docs") {
      url("https://apidocs.arrow-kt.io/")
    }
  }
}
