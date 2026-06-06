package strikt.assertions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

internal class ListAssertions {
  @Nested
  inner class ContainsSequenceAssertion {
    @Nested
    inner class AList {
      private val fixture = listOf(1, 2, 3, 4)

      @Nested
      inner class PassesIf {

        @Test
        fun `the subject contains the sequence`() {
              expectThat(fixture)
                .containsSequence(3, 4)
        }

        @Test
        fun `the subject contains exactly the same elements as the expected sequence`() {
              expectThat(fixture)
                .containsSequence(1, 2, 3, 4)
        }
      }

      @Nested
      inner class FailsIf {
        @Test
        fun `the subject does not contain the sequence in the same order`() {
              val exception =
                assertThrows<AssertionError> {
                  expectThat(fixture)
                    .containsSequence(1, 4)
                }
              expectThat(exception.message).isEqualTo(
                """▼ Expect that [1, 2, 3, 4]:
            |  ✗ contains the sequence: [1, 4] in exactly the same order
                """.trimMargin()
              )
        }

        @Test
        fun `the expected sequence is longer than the subject`() {
              val exception =
                assertThrows<AssertionError> {
                  expectThat(fixture)
                    .containsSequence(1, 2, 3, 4, 5)
                }
              expectThat(exception.message).isEqualTo(
                """▼ Expect that [1, 2, 3, 4]:
              |  ✗ contains the sequence: [1, 2, 3, 4, 5] in exactly the same order : expected sequence cannot be longer than subject
                """.trimMargin()
              )
        }

        @Test
        fun `the expected sequence is empty`() {
              val exception =
                assertThrows<AssertionError> {
                  expectThat(fixture)
                    .containsSequence()
                }
              expectThat(exception.message).isEqualTo(
                """▼ Expect that [1, 2, 3, 4]:
            |  ✗ contains the sequence: [] in exactly the same order : expected sequence cannot empty
                """.trimMargin()
              )
        }
      }
    }
  }
}
