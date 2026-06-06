package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat

internal class ResultAssertions {

  @Test
  fun `when result is a failure the isFailure assertion passes`() {
    expectThat(Result.failure<Int>(Exception("boom")))
      .isFailure()
      .isA<Exception>()
      .message
      .isEqualTo("boom")
  }

  @Test
  fun `when result is a success the isFailure assertion fails`() {
    assertThrows<AssertionFailedError> {
      expectThat(Result.success(42))
        .isFailure()
    }
  }

  @Test
  fun `when result is a success the isSuccess assertion passes`() {
    expectThat(Result.success(42))
      .isSuccess()
      .isEqualTo(42)
  }

  @Test
  fun `when result is a failure the isSuccess assertion fails`() {
    assertThrows<AssertionFailedError> {
      expectThat(Result.failure<Int>(Exception()))
        .isSuccess()
    }
  }
}
