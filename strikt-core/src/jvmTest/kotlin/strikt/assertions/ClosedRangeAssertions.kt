package strikt.assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import strikt.api.Assertion
import strikt.api.expectThat

@DisplayName("assertions on ClosedRange")
internal class ClosedRangeAssertions {
    @Nested
    inner class Contains {
      @Nested
      inner class AnEmptyIntRange {
        private val fixture: Assertion.Builder<ClosedRange<Int>> = expectThat(IntRange.EMPTY)

        @Test
        fun `fails to contain any value`() {
          fixture.run {
            assertThrows<AssertionError> {
              contains(0)
            }
          }
        }
      }

      @Nested
      inner class AnIntRangeFrom1To4 {
        private val fixture: Assertion.Builder<ClosedRange<Int>> = expectThat(1..4)

        @Test
        fun `fails to contain 0`() {
          fixture.run {
            assertThrows<AssertionError> {
              contains(0)
            }
          }
        }

      @TestFactory
      fun `passes for containing values in range`() {
        fixture.run {
          (1..4).forEach { value ->
            DynamicTest.dynamicTest("assertion passes for containing $value") {
              contains(value)
            }
          }
        }
      }
    }
  }

    @Nested
    inner class IsEmpty {
      @Nested
      inner class AnEmptyIntRange {
        val fixture = expectThat(IntRange.EMPTY)

        @Test
        fun `an empty Int succeeds`() {
          fixture.run {
            isEmpty()
          }
        }
      }

      @Nested
      inner class ANonEmptyIntRange {
        val fixture = expectThat(2..2)

        @Test
        fun `a nonempty IntRange fails`() {
          fixture.run {
            assertThrows<AssertionError> {
              isEmpty()
            }
          }
        }
      }
    }
}
