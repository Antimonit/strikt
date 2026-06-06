@file:Suppress("KDocMissingDocumentation")

plugins {
  id("strikt.gradle.kotlin-jvm")
  alias(libs.plugins.published)
  alias(libs.plugins.protobuf)
}

description = "Extensions for testing code that uses Protobuf / gRPC."

dependencies {
  api(project(":strikt-core"))

  compileOnly(libs.protobuf)
  testImplementation(libs.protobuf)
}

dokka {
  dokkaSourceSets.configureEach {
    externalDocumentationLinks.register("protobuf-docs") {
      url("https://protobuf.dev/reference/java/api-docs/")
    }
  }
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.java.get()}"
  }
  generateProtoTasks {
    ofSourceSet("test")
  }
}

sourceSets {
  getByName("test") {
    kotlin.srcDirs(
      "src/test/kotlin",
      "$buildFile/generated/source/proto/test/java"
    )
  }
}
