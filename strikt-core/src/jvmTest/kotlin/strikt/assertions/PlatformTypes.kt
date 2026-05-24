package strikt.assertions

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import strikt.api.expectThat
import strikt.java.PersonJava

internal object PlatformTypes : JUnit5Minutests {
  fun tests() =
    rootContext {
      context("when nullability is uncertain") {
        val expectation =
          expectThat(PersonJava("Oswald Launcelot Campbell-Graves"))
            .get(PersonJava::getName)
        test("isNotNull can be applied") {
          expectation.isNotNull()
        }
      }
    }
}
