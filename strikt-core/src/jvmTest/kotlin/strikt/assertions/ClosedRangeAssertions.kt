package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

internal class ClosedRangeAssertions {

  @Test
  fun `contains fails when the range is empty`() {
    assertThrows<AssertionError> {
      expectThat<ClosedRange<Int>>(IntRange.EMPTY).contains(0)
    }
  }

  @Test
  fun `contains fails when the value is out of the range`() {
    assertThrows<AssertionError> {
      expectThat<ClosedRange<Int>>(1..4).contains(0)
    }
  }

  @Test
  fun `contains passes when the value is within the range`() {
    (1..4).forEach { value ->
      expectThat<ClosedRange<Int>>(1..4).contains(value)
    }
  }

  @Test
  fun `isEmpty passes when the range is empty`() {
    expectThat<ClosedRange<Int>>(IntRange.EMPTY).isEmpty()
  }

  @Test
  fun `isEmpty fails when the range is not empty`() {
    assertThrows<AssertionError> {
      expectThat<ClosedRange<Int>>(2..2).isEmpty()
    }
  }
}
