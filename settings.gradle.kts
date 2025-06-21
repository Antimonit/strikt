rootProject.name = "strikt"

include(
  "strikt-bom",
  "strikt-core",
  "site",
  "strikt-arrow",
  "strikt-jackson",
  "strikt-jvm",
  "strikt-mockk",
  "strikt-protobuf",
  "strikt-spring"
)

rootProject.children.forEach {
  it.buildFileName = "${it.name}.gradle.kts"
}

pluginManagement {
  includeBuild("buildLogic")

  plugins {
    val versions = mapOf<String, String>()
      .withDefault { extra["versions.$it"].toString() }

    kotlin("jvm") version versions.getValue("kotlin")
    id("org.jetbrains.kotlin.plugin.spring") version versions.getValue("kotlin")
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    @Suppress("DEPRECATION")
    jcenter {
      content { includeGroup("io.github.javaeden.orchid") }
    }
  }
}
