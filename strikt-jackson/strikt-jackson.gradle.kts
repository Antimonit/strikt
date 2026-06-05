plugins {
  id("strikt.gradle.kotlin-jvm")
  alias(libs.plugins.published)
}

description = "Extensions for assertions and traversals on types Jackson's JsonNode and sub-types."

dependencies {
  api(project(":strikt-core"))

  compileOnly(platform(libs.jackson.bom))
  compileOnly(libs.jackson.databind)

  testImplementation(platform(libs.jackson.bom))
  testImplementation(libs.jackson.module.kotlin)
  testImplementation(libs.minutest)
}

dokka {
  dokkaSourceSets.configureEach {
    externalDocumentationLinks.register("jackson-docs") {
      url("https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-databind/${libs.versions.jackson.get()}/")
    }
  }
}
