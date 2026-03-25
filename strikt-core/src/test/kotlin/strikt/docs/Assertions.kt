package strikt.docs

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import strikt.api.expect
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.all
import strikt.assertions.hasLength
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import strikt.assertions.isLowerCase
import strikt.assertions.isSuccess
import strikt.assertions.isUpperCase
import strikt.assertions.message
import strikt.assertions.startsWith
import strikt.internal.opentest4j.CompoundAssertionFailure

@DisplayName("Snippets used in Orchid docs")
internal class Assertions {
  // assertion-styles.md
// -----------------------------------------------------------------------------

  @Test fun `assertion styles 1, 2`() {
    val s =
      """
      // --8<-- [start:assertion_styles_2]
      ▼ Expect that "fnord":
        ✓ is an instance of java.lang.String
        ✗ has length 1
               found 5
      // --8<-- [end:assertion_styles_2]
      """

    expectThrows<AssertionFailedError> {
      // --8<-- [start:assertion_styles_1]
      val subject = "fnord"
      expectThat(subject)
        .isA<String>()
        .hasLength(1)
        .isUpperCase()
      // --8<-- [end:assertion_styles_1]
    }
      .message
      .isEqualTo(s.removeSnippetTags().trimIndent().trim())
  }

  @Test fun `assertion styles 3, 4`() {
    val s =
      """
      // --8<-- [start:assertion_styles_4]
      ▼ Expect that "fnord":
        ✓ is an instance of java.lang.String
        ✗ has length 1
               found 5
        ✗ is upper case
      // --8<-- [end:assertion_styles_4]
      """

    expectThrows<CompoundAssertionFailure> {
      // --8<-- [start:assertion_styles_3]
      val subject = "fnord"
      expectThat(subject) {
        isA<String>()
        hasLength(1)
        isUpperCase()
      }
      // --8<-- [end:assertion_styles_3]
    }
      .message
      .isEqualTo(s.removeSnippetTags().trimIndent().trim())
  }

  @Test fun `assertion styles 5, 6`() {
    val s =
      """
      // --8<-- [start:assertion_styles_6]
      ▼ Expect that 1:
        ✗ is less than 1
        ✗ is greater than 1
      // --8<-- [end:assertion_styles_6]
      """

    expectThrows<CompoundAssertionFailure> {
      // --8<-- [start:assertion_styles_5]
      val subject = 1L
      expectThat(subject) {
        isLessThan(1L).isA<Int>()
        isGreaterThan(1L)
      }
      // --8<-- [end:assertion_styles_5]
    }
      .message
      .isEqualTo(s.removeSnippetTags().trimIndent().trim())
  }

  @Test fun `assertion styles 7, 8`() {
    val s =
      """
      // --8<-- [start:assertion_styles_8]
      ▼ Expect that "fnord":
        ✓ is an instance of java.lang.String
        ✗ has length 1
               found 5
      ▼ Expect that 1:
        ✗ is less than 1
        ✗ is greater than 1
      // --8<-- [end:assertion_styles_8]
      """

    expectThrows<CompoundAssertionFailure> {
      // --8<-- [start:assertion_styles_7]
      expect {
        that("fnord")
          .isA<String>()
          .hasLength(1)
          .isUpperCase()
        that(1L) {
          isLessThan(1L).isA<Int>()
          isGreaterThan(1L)
        }
      }
      // --8<-- [end:assertion_styles_7]
    }
      .message
      .isEqualTo(s.removeSnippetTags().trimIndent().trim())
  }

// collection-elements.md
// -----------------------------------------------------------------------------

  @Test fun `collections 1, 2`() {
    val s =
      """
      // --8<-- [start:collections_1]
      ▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        ✗ all elements match:
          ▼ "catflap":
            ✓ is lower case
            ✓ starts with 'c'
          ▼ "rubberplant":
            ✓ is lower case
            ✗ starts with 'c'
                    found 'r'
          ▼ "marzipan":
            ✓ is lower case
            ✗ starts with 'c'
                    found 'm'
      // --8<-- [end:collections_1]
      """

    expectThrows<AssertionFailedError> {
      // --8<-- [start:collections_2]
      val subject = setOf("catflap", "rubberplant", "marzipan")
      expectThat(subject).all {
        isLowerCase()
        startsWith('c')
      }
      // --8<-- [end:collections_2]
    }
      .message
      .isEqualTo(s.removeSnippetTags().trimIndent().trim())
  }

// expecting-exceptions.md
// -----------------------------------------------------------------------------

  @Test fun `catching exceptions 1, 2, 3`() {
    // --8<-- [start:catching_exceptions_1]
    expectCatching { identifyHotdog("hamburger") }
      .isFailure()
      .isA<NotHotdogException>()
    // --8<-- [end:catching_exceptions_1]

    // --8<-- [start:catching_exceptions_2]
    expectCatching { identifyHotdog("hamburger") }
      .isFailure()
    // --8<-- [end:catching_exceptions_2]

    // --8<-- [start:catching_exceptions_3]
    expect {
      catching { identifyHotdog("hamburger") }
        .isFailure()
        .isA<NotHotdogException>()

      catching { identifyHotdog("hotdog") }
        .isSuccess()
    }
    expectCatching { identifyHotdog("hotdog") }
      .isSuccess()
    // --8<-- [end:catching_exceptions_3]
  }

  @Test fun `expect_throws 1`() {
    // --8<-- [start:expect_throws_1]
    expectThrows<NotHotdogException> {
      identifyHotdog("hamburger")
    }
    // --8<-- [end:expect_throws_1]
  }

  private fun identifyHotdog(food: String): String {
    if (food != "hotdog") throw NotHotdogException()
    return food
  }

  private class NotHotdogException : Exception()

// flow-typing.md
// -----------------------------------------------------------------------------

  @Test fun `flow typing 1`() {
    // --8<-- [start:flow_typing_1]
    val subject: Map<String, Any> = mapOf("count" to 1, "name" to "Rob")
    expectThat(subject["count"])
      .isA<Int>()
      .isGreaterThan(0)

    expectThat(subject["name"])
      .isA<String>()
      .hasLength(3)
    // --8<-- [end:flow_typing_1]
  }
}

fun String.removeSnippetTags(): String {
  return this
    .replace("// --8<-- \\[[^]]+].*$".toRegex(RegexOption.MULTILINE), "")
}
