package strikt.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

fun Project.commonTest() {
  tasks.withType<Test> {
    systemProperty("junit.jupiter.execution.parallel.enabled", "false")
    useJUnitPlatform {
      includeEngines("junit-jupiter", "failgood")
    }
  }
}
