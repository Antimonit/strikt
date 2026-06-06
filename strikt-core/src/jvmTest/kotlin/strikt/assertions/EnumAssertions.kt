package strikt.assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat
import strikt.api.expectThrows

@DisplayName("assertions on enums")
internal class EnumAssertions {
  @Test
  fun `name mapping`() {
    Pantheon.entries.forEach { deity ->
      expectThat(deity).name.isEqualTo(deity.name)
    }
  }

  @Test
  fun `ordinal mapping`() {
    Pantheon.entries.forEach { deity ->
      expectThat(deity).ordinal.isEqualTo(deity.ordinal)
    }
  }

  @Test
  fun `isOneOf passes if the subject is one of the specified values`() {
    expectThat(Pantheon.NORSE).isOneOf(Pantheon.NORSE, Pantheon.GREEK)
  }

  @Test
  fun `isOneOf fails if the subject is not one of the specified values`() {
    expectThrows<AssertionFailedError> {
      expectThat(Pantheon.NORSE).isOneOf(Pantheon.ROMAN, Pantheon.GREEK)
    }
  }
}

enum class Pantheon(val ruler: String, val underworldRuler: String) {
  NORSE("Odin", "Hel"),
  GREEK("Zeus", "Hades"),
  ROMAN("Jupiter", "Pluto")
}

enum class Deity(val realm: Pantheon) {
  Eris(Pantheon.GREEK)
}
