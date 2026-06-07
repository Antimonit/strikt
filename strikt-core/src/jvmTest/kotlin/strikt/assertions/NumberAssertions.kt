package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

internal class NumberAssertions {

  @Test
  fun `can compare two doubles with a tolerance`() {
    expectThat(5.0).isEqualTo(5.001, 0.1)
    expectThat(5.0).isEqualTo(5.0001, 0.001)
  }

  @Test
  fun `isEqualTo within tolerance works with floats`() {
    expectThat(5.0f).isEqualTo(5.001f, 0.1)
  }

  @Test
  fun `fails if expected value is outside of tolerance`() {
    assertThrows<AssertionError> {
      expectThat(5.0).isEqualTo(5.11, 0.1)
    }
    assertThrows<AssertionError> {
      expectThat(5.0).isEqualTo(6.0, 0.999)
    }
    assertThrows<AssertionError> {
      expectThat(5.0).isEqualTo(5.10000001, 0.1)
    }
  }
}
