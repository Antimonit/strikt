package strikt.assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat

@DisplayName("assertions on CharSequence")
internal class CharSequenceAssertions {
  private val subject = expectThat("fnord")

    @Nested
    inner class HasLength {
      @Test
      fun `passes when the subject has the expected length`() {
        subject.run {
          hasLength(5)
        }
      }

      @Test
      fun `fails when the subject does not have the expected length`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            hasLength(1)
          }
        }
      }
    }

    @Nested
    inner class Matches {
      @Test
      fun `passes when the subject is a full match for the regex`() {
        subject.run {
          matches("[dfnor]+".toRegex())
        }
      }

      @Test
      fun `fails when the subject is only a partial match for the regex`() {
        assertThrows<AssertionFailedError> {
          subject.run {
            matches("[fn]+".toRegex())
          }
        }
      }

      @Test
      fun `fails when the subject is a case insensitive match for the regex`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            matches("[DFNOR]+".toRegex())
          }
        }
      }

      @Test
      fun `fails when the subject does not match the regex`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            matches("\\d+".toRegex())
          }
        }
      }
    }

    @Nested
    inner class MatchesIgnoringCase {
      @Test
      fun `passes when the subject is a full match for the regex`() {
        subject.run {
          matchesIgnoringCase("[dfnor]+".toRegex())
        }
      }

      @Test
      fun `fails when the subject is only a partial match for the regex`() {
        assertThrows<AssertionFailedError> {
          subject.run {
            matchesIgnoringCase("[fn]+".toRegex())
          }
        }
      }

      @Test
      fun `passes when the subject is a case insensitive match for the regex`() {
        subject.run {
          matchesIgnoringCase("[DFNOR]+".toRegex())
        }
      }

      @Test
      fun `fails when the subject does not match the regex`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            matchesIgnoringCase("\\d+".toRegex())
          }
        }
      }
    }

    @Nested
    inner class ContainsRegex {
      @Test
      fun `passes when the subject is a full match for the regex`() {
        subject.run {
          contains("[dfnor]+".toRegex())
        }
      }

      @Test
      fun `passes when the subject is only a partial match for the regex`() {
        subject.run {
          contains("[fn]+".toRegex())
        }
      }

      @Test
      fun `fails when the subject contains a match with a different case`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            contains("[DFNOR]+".toRegex())
          }
        }
      }

      @Test
      fun `fails when the subject does not match the regex`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            contains("\\d+".toRegex())
          }
        }
      }
    }

    @Nested
    inner class ContainsIgnoringCaseRegex {
      @Test
      fun `passes when the subject is a full match for the regex`() {
        subject.run {
          containsIgnoringCase("[dfnor]+".toRegex())
        }
      }

      @Test
      fun `passes when the subject is only a partial match for the regex`() {
        subject.run {
          containsIgnoringCase("[fn]+".toRegex())
        }
      }

      @Test
      fun `passes when the subject contains a match with a different case`() {
        subject.run {
          containsIgnoringCase("[FN]+".toRegex())
        }
      }

      @Test
      fun `fails when the subject does not match the regex`() {
        subject.run {
          assertThrows<AssertionFailedError> {
            containsIgnoringCase("\\d+".toRegex())
          }
        }
      }
    }

    @Nested
    inner class ContainsCharSequence {
      @Test
      fun `passes when the subject contains the expected substring`() {
        subject.run {
          contains("nor")
        }
      }

      @Test
      fun `fails when the subject contains the expected substring in a different case`() {
        assertThrows<AssertionFailedError> {
          subject.run {
            contains("NOR")
          }
        }
      }

      @Test
      fun `fails when the subject does not contain the expected substring`() {
        assertThrows<AssertionFailedError> {
          subject.run {
            contains("meme")
          }
        }
      }
    }

    @Nested
    inner class ContainsIgnoringCaseCharSequence {
      @Test
      fun `passes when the subject contains the expected substring`() {
        subject.run {
          containsIgnoringCase("nor")
        }
      }

      @Test
      fun `passes when the subject contains the expected substring in a different case`() {
        subject.run {
          containsIgnoringCase("NOR")
        }
      }

      @Test
      fun `fails when the subject does not contain the expected substring`() {
        assertThrows<AssertionFailedError> {
          subject.run {
            containsIgnoringCase("meme")
          }
        }
      }
    }

    @Nested
    inner class IsNullOrEmpty {
      @TestFactory
      fun `the assertion passes`() {
        listOf("", null).forEach<CharSequence?> { value ->
          val fixture = expectThat(value)

          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${value.quoted()}") {
              isNullOrEmpty()
            }
          }
        }
      }

      @TestFactory
      fun `the assertion fails`() {
        listOf("catflap", " ", "\t", "a", "23", "[]")
          .forEach<CharSequence?> { subject ->
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              val fixture = expectThat(subject)

              fixture.run {
                assertThrows<AssertionFailedError> {
                  isNullOrEmpty()
                }
              }
            }
          }
      }
    }

    @Nested
    inner class IsNullOrBlank {
      @TestFactory
      fun `the assertion passes`() {
        listOf("", null, "\t", "     ", " \n \r\n\t\n")
          .forEach<CharSequence?> { subject ->
            val fixture = expectThat(subject)
            fixture.run {
              DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
                isNullOrBlank()
              }
            }
          }
      }

      @TestFactory
      fun `the assertion fails`() {
        listOf("catflap", "a", "73", "[]").forEach<CharSequence?> { subject ->
          val fixture = expectThat(subject)

          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              assertThrows<AssertionFailedError> {
                isNullOrBlank()
              }
            }
          }
        }
      }
    }

      @Nested
      inner class IsEmpty {
        @Test
        fun `the assertion passes when the subject is empty`() {
          val fixture = expectThat("")

          fixture.run {
            isEmpty()
          }
        }

      @TestFactory
      fun `the assertion fails`() {
        listOf("catflap", " ", "\t", "a", "73", "[]").forEach { subject ->
          val fixture = expectThat(subject)

          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              assertThrows<AssertionFailedError> {
                isEmpty()
              }
            }
          }
        }
      }
    }

    @Nested
    inner class IsBlank {
      @TestFactory
      fun `the assertion passes`() {
        listOf(
          "",
          "\t",
          "     ",
          " \n \r\n\t\n"
        ).forEach<CharSequence> { subject ->
          val fixture = expectThat(subject)

          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              isBlank()
            }
          }
        }
      }

      @TestFactory
      fun `the assertion fails`() {
        listOf("catflap", "a", "23", "[]").forEach<CharSequence> { subject ->
          val fixture = expectThat(subject)

          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              assertThrows<AssertionFailedError> {
                isBlank()
              }
            }
          }
        }
      }
    }

    @Nested
    inner class IsNotEmpty {
      @Test
      fun `the assertion fails when the subject is empty`() {
        val fixture = expectThat("")

        fixture.run {
          assertThrows<AssertionFailedError> {
            isNotEmpty()
          }
        }
      }

      @TestFactory
      fun `the assertion passes`() {
        listOf("catflap", " ", "\t", "a", "73", "[]")
          .forEach<CharSequence> { subject ->
            val fixture = expectThat(subject)

            fixture.run {
              DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
                fixture.isNotEmpty()
              }
            }
          }
      }
    }

    @Nested
    inner class IsNotBlank {
      @TestFactory
      fun `the assertion fails`() {
        listOf(
          "",
          "\t",
          "     ",
          " \n \r\n\t\n"
        ).forEach<CharSequence> { subject ->
          val fixture = expectThat(subject)

          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              assertThrows<AssertionFailedError> {
                isNotBlank()
              }
            }
          }
        }
      }

      @TestFactory
      fun `the assertion passes`() {
        listOf("catflap", "a", "73", "[]").forEach { subject ->
          val fixture = expectThat(subject)
          fixture.run {
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()}") {
              isNotBlank()
            }
          }
        }
      }
    }

    @Nested
    inner class Trim {
      @Test
      fun `can trim char sequence`() {
        val fixture = expectThat(StringBuilder(" fnord "))

        fixture.run {
          trim().isEqualTo("fnord")
        }
      }
    }
}
