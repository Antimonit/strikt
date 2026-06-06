package strikt.assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat
import strikt.api.expectThrows

@DisplayName("assertions on enums")
internal class EnumAssertions {
  @TestFactory
  fun `name mapping`() {
        Pantheon.entries.forEach { deity ->
          DynamicTest.dynamicTest("Can get name on $deity") {
            expectThat(deity).name.isEqualTo(deity.name)
          }
        }
  }

  @TestFactory
  fun `ordinal mapping`() {
        Pantheon.entries.forEach { deity ->
          DynamicTest.dynamicTest("Can get ordinal on $deity") {
            expectThat(deity).ordinal.isEqualTo(deity.ordinal)
          }
        }
  }

  @Nested
  inner class IsOneOf {
    @Test
    fun `passes if the subject is one of the specified values`() {
          expectThat(Pantheon.NORSE).isOneOf(Pantheon.NORSE, Pantheon.GREEK)
    }

    @Test
    fun `fails if the subject is not one of the specified values`() {
          expectThrows<AssertionFailedError> {
            expectThat(Pantheon.NORSE).isOneOf(Pantheon.ROMAN, Pantheon.GREEK)
          }
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
