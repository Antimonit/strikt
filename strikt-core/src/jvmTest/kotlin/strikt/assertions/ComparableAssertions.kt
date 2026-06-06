package strikt.assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

@DisplayName("assertions on Comparable")
internal object ComparableAssertions {
  private fun <T : Comparable<T>> supportsComparisonAssertions(
    value: T,
    decrementor: T.() -> T,
    incrementor: T.() -> T
  ): List<DynamicNode> {
    val fixture = expectThat(value)

  return fixture.run {
    listOf(
  DynamicContainer.dynamicContainer(
    "isGreaterThan assertion",
    listOf(
      DynamicTest.dynamicTest("passes if the subject is greater than the expected value") {
        isGreaterThan(value.decrementor())
      },

      DynamicTest.dynamicTest("fails if the subject is equal to the expected value") {
        assertThrows<AssertionError> {
          isGreaterThan(value)
        }
      },

      DynamicTest.dynamicTest("fails if the subject is less than the expected value") {
        assertThrows<AssertionError> {
          isGreaterThan(value.incrementor())
        }
      }
    )
  ),

  DynamicContainer.dynamicContainer(
    "isGreaterThanOrEqualTo assertion",
    listOf(
      DynamicTest.dynamicTest("passes if the subject is greater than the expected value") {
        isGreaterThanOrEqualTo(value.decrementor())
      },

      DynamicTest.dynamicTest("passes if the subject is equal to the expected value") {
        isGreaterThanOrEqualTo(value)
      },

      DynamicTest.dynamicTest("fails if the subject is less than the expected value") {
        assertThrows<AssertionError> {
          isGreaterThanOrEqualTo(value.incrementor())
        }
      }
    )
  ),

  DynamicContainer.dynamicContainer(
    "isLessThan assertion",
    listOf(
      DynamicTest.dynamicTest("fails if the subject is greater than the expected value") {
        assertThrows<AssertionError> {
          isLessThan(value.decrementor())
        }
      },

      DynamicTest.dynamicTest("fails if the subject is equal to the expected value") {
        assertThrows<AssertionError> {
          isLessThan(value)
        }
      },

      DynamicTest.dynamicTest("passes if the subject is less than the expected value") {
        isLessThan(value.incrementor())
      }
    )
  ),

  DynamicContainer.dynamicContainer(
    "isLessThanOrEqualTo assertion",
    listOf(
      DynamicTest.dynamicTest("fails if the subject is greater than the expected value") {
        assertThrows<AssertionError> {
          isLessThanOrEqualTo(value.decrementor())
        }
      },

      DynamicTest.dynamicTest("passes if the subject is equal to the expected value") {
        isLessThanOrEqualTo(value)
      },

      DynamicTest.dynamicTest("passes if the subject is less than the expected value") {
        isLessThanOrEqualTo(value.incrementor())
      }
    )
  ),

  DynamicContainer.dynamicContainer(
    "comparesEqualTo assertion",
    listOf(
      DynamicTest.dynamicTest("fails if the subject is greater than the expected value") {
        assertThrows<AssertionError> {
          comparesEqualTo(value.decrementor())
        }
      },

      DynamicTest.dynamicTest("fails if the subject is less than the expected value") {
        assertThrows<AssertionError> {
          comparesEqualTo(value.incrementor())
        }
      },

      DynamicTest.dynamicTest("passes if the subject is equal to the expected value") {
        comparesEqualTo(value)
      }
    )
  )
    )
  }
}

  @TestFactory
  fun comparableAssertions_Int(): List<DynamicNode> =
    listOf(
      DynamicContainer.dynamicContainer(
        "an Int subject",
        supportsComparisonAssertions(1, Int::dec, Int::inc)
      )
    )

  @TestFactory
  fun comparableAssertions_Instant(): List<DynamicNode> =
    listOf(
      DynamicContainer.dynamicContainer(
        "an Instant subject",
        supportsComparisonAssertions(
          Instant.parse("2026-12-31T12:30:00Z"),
          { minus(1.milliseconds) },
          { plus(1.milliseconds) }
        )
      )
    )

  @TestFactory
  fun comparableAssertions_String(): List<DynamicNode> =
    listOf(
      DynamicContainer.dynamicContainer(
        "a String subject",
        supportsComparisonAssertions("a", { "A" }, { "z" })
      )
    )

  @TestFactory
  fun isIn_Int(): List<DynamicNode> {
    val range = 1..10
    return listOf(
      DynamicContainer.dynamicContainer(
        "values in range",
        range.map { i ->
          val fixture = expectThat(i)

          DynamicTest.dynamicTest("$i is in the range ${range.start}..${range.endInclusive}") {
            expectThat(i).isIn(range)
          }
        }
      ),

      DynamicContainer.dynamicContainer(
        "values out of range",
        ((-5..0) + (11..15)).map { i ->
          val fixture = expectThat(i)

          DynamicTest.dynamicTest("$i is not in the range ${range.start}..${range.endInclusive}") {
            assertThrows<AssertionError> {
              expectThat(i).isIn(range)
            }
          }
        }
      )
    )
  }

  @TestFactory
  fun isIn_Long(): List<DynamicContainer> {
    val range = 1L..10L
    return listOf(
      DynamicContainer.dynamicContainer(
        "values in range",
        range.map { i ->
          val fixture = expectThat(i)

          DynamicTest.dynamicTest("$i is in the range ${range.start}..${range.endInclusive}") {
            expectThat(i).isIn(range)
          }
        }
      ),

      DynamicContainer.dynamicContainer(
        "values out of range",
        ((-5L..0L) + (11L..15L)).map { i ->
          val fixture = expectThat(i)

          DynamicTest.dynamicTest("$i is not in the range ${range.start}..${range.endInclusive}") {
            assertThrows<AssertionError> {
              expectThat(i).isIn(range)
            }
          }
        }
      )
    )
  }
}
