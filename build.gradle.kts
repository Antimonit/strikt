import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.codearte.gradle.nexus.NexusStagingExtension
import kotlin.text.RegexOption.IGNORE_CASE

plugins {
  alias(libs.plugins.dokka)
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.spring) apply false
  alias(libs.plugins.nexus.staging)
  alias(libs.plugins.kotlinter) apply false
  alias(libs.plugins.test.logger) apply false
  alias(libs.plugins.versions)
  alias(libs.plugins.kover)
}

allprojects {
  group = "io.strikt"

  configurations.all {
    resolutionStrategy.eachDependency {
      if (requested.group == "org.jetbrains.kotlin") {
        useVersion(libs.versions.kotlin.get())
      }
    }
  }
}

tasks.dokkaHtmlMultiModule {
  outputDirectory.set(rootDir.resolve("docs/api"))
}

configure<NexusStagingExtension> {
  stagingProfileId = "3fc70880a122f"
}

// Dependency updates configuration
fun ModuleComponentIdentifier.isNonStable() =
  version.contains(Regex("""-(m|eap|rc|alpha|beta|b)([-\.]?[\d-]+)?""", IGNORE_CASE))

tasks.withType<DependencyUpdatesTask> {
  revision = "release"
  checkConstraints = true
  gradleReleaseChannel = "current"
  checkForGradleUpdate = true
  rejectVersionIf {
    candidate.isNonStable()
  }
}
