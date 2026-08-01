package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

internal class BooleanAssertions {

  @Test
  fun `isTrue passes when the subject is true`() {
    expectThat(true).isTrue()
  }

  @Test
  fun `isTrue fails when the subject is false`() {
    assertThrows<AssertionError> {
      expectThat(false).isTrue()
    }
  }

  @Test
  fun `isTrue fails when the subject is null`() {
    assertThrows<AssertionError> {
      expectThat(null).isTrue()
    }
  }

  @Test
  fun `isFalse passes when the subject is false`() {
    expectThat(false).isFalse()
  }

  @Test
  fun `isFalse fails when the subject is true`() {
    assertThrows<AssertionError> {
      expectThat(true).isFalse()
    }
  }

  @Test
  fun `isFalse fails when the subject is null`() {
    assertThrows<AssertionError> {
      expectThat(null).isFalse()
    }
  }
}
