package strikt.assertions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat

internal class ResultAssertions {
  @Nested
  inner class IsFailure {

    @Nested
    inner class WhenResultIsAFailure {
      private val subject = expectThat(Result.failure<Int>(Exception("boom")))

      @Test
      fun `the assertion passes`() {
        subject.run {
            isFailure().isA<Exception>().message.isEqualTo("boom")
        }
      }
    }

    @Nested
    inner class WhenResultIsASuccess {
      private val subject = expectThat(Result.success(42))

      @Test
      fun `the assertion fails`() {
        subject.run {
            assertThrows<AssertionFailedError> {
              isFailure()
            }
        }
      }
    }
  }

  @Nested
  inner class IsSuccess {

    @Nested
    inner class WhenResultIsASuccess {
      private val subject = expectThat(Result.success(42))

      @Test
      fun `the assertion passes`() {
        subject.run {
            isSuccess().isEqualTo(42)
        }
      }
    }

    @Nested
    inner class WhenResultIsAFailure {
      private val subject = expectThat(Result.failure<Int>(Exception()))

      @Test
      fun `the assertion fails`() {
        subject.run {
            assertThrows<AssertionFailedError> {
              isSuccess()
            }
        }
      }
    }
  }
}
