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
    // needed for dokka plugin, feels like this belongs in published.gradle.kts but it doesn't work there
    maven {
      url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }
    @Suppress("DEPRECATION")
    jcenter {
      content { includeGroup("io.github.javaeden.orchid") }
    }
  }
}
