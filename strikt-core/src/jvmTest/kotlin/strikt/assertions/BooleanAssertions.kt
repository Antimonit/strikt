package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

@Suppress("SimplifyBooleanWithConstants")
internal class BooleanAssertions {

  @Test
  fun `isTrue passes when the subject is true`() {
    expectThat("a" == "a").isTrue()
  }

  @Test
  fun `isTrue fails when the subject is false`() {
    assertThrows<AssertionError> {
      expectThat("a" == "A").isTrue()
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
    expectThat("a" == "A").isFalse()
  }

  @Test
  fun `isFalse fails when the subject is true`() {
    assertThrows<AssertionError> {
      expectThat("a" == "a").isFalse()
    }
  }

  @Test
  fun `isFalse fails when the subject is null`() {
    assertThrows<AssertionError> {
      expectThat(null).isFalse()
    }
  }
}
