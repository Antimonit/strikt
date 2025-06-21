plugins {
  `java-platform`
  alias(libs.plugins.published)
}

description =
  "Bill of materials to make sure a consistent set of versions is used for Strikt."

publishing {
  publications {
    getByName<MavenPublication>("nebula") {
      from(components["javaPlatform"])
    }
  }
}

dependencies {
  constraints {
    api(project(":strikt-arrow"))
    api(project(":strikt-core"))
    api(project(":strikt-jackson"))
    api(project(":strikt-jvm"))
    api(project(":strikt-mockk"))
    api(project(":strikt-protobuf"))
    api(project(":strikt-spring"))
  }
}
