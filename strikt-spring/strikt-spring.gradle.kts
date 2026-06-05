plugins {
  id("strikt.gradle.kotlin-jvm")
  alias(libs.plugins.published)
  alias(libs.plugins.kotlin.spring)
}

description = "Extensions for testing code that uses the Spring Framework."

dependencies {

  api(project(":strikt-core"))

  implementation(platform(libs.spring.boot))
  compileOnly(libs.spring.test)
  compileOnly(libs.spring.web)
  compileOnly(libs.jakarta.servlet.api)

  testImplementation(libs.minutest)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.spring.boot.starter.web)
}

dokka {
  dokkaSourceSets.configureEach {
    externalDocumentationLinks.register("spring-docs") {
      // Not all APIs are contained in the kotlin kdoc.
      // For example, the whole `org.springframework.http` package from
      // the `spring-web` artifact can be only found in javadoc.
      url("https://docs.spring.io/spring-framework/docs/current/javadoc-api/")
      url("https://docs.spring.io/spring-framework/docs/current/kdoc-api/")
      // Furthermore, Spring javadoc does not publish the package-list.
      packageListUrl("https://docs.spring.io/spring-framework/docs/current/kdoc-api/package-list")
    }
  }
}
