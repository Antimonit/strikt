package strikt.assertions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.java.PersonJava

internal class PlatformTypes {

  @Nested
  inner class WhenNullabilityIsUncertain {
        val expectation =
          expectThat(PersonJava("Oswald Launcelot Campbell-Graves"))
            .get(PersonJava::getName)

    @Test
    fun `isNotNull can be applied`() {
          expectation.isNotNull()
    }
  }
}
