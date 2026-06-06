package strikt

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import strikt.assertions.isSuccess
import strikt.assertions.message

internal class Catching {
  @Nested
  inner class ASuccessfulAction {
        private val subject = run {
          expectCatching { "kthxbye" }
        }

    @Test
    fun `maps to the action's returned value`() {
      run {
        subject.run {
          isSuccess()
            .isA<String>()
            .isEqualTo("kthxbye")
        }
      }
    }

    @Test
    fun `is not failed`() {
      run {
        subject.run {
          assertThrows<AssertionFailedError> {
            isFailure()
          }
        }
      }
    }

    @Test
    fun `chains correctly in a block`() {
      run {
        subject.run {
          assertThrows<AssertionError> {
            and {
              isFailure().isA<NullPointerException>()
            }
          }.also { exception ->
            expectThat(exception.message).isEqualTo(
              """
            |▼ Expect that Success(kthxbye):
            |  ✗ is failure
            |    returned "kthxbye"
              """.trimMargin()
            )
          }
        }
      }
    }
  }

  @Nested
  inner class AFailedAction {
        private val subject = run {
          expectCatching { error("o noes") }
        }

    @Test
    fun `maps to the exception thrown by the action`() {
      run {
        subject.run {
          isFailure()
            .isA<IllegalStateException>()
            .message
            .isEqualTo("o noes")
        }
      }
    }

    @Test
    fun `is not successful`() {
      run {
        subject.run {
          assertThrows<AssertionFailedError> {
            isSuccess()
          }
        }
      }
    }

    @Test
    fun `chains correctly in a block`() {
      run {
        subject.run {
          assertThrows<AssertionError> {
            and {
              isSuccess().isA<String>()
            }
          }.also { exception ->
            expectThat(exception.message).isEqualTo(
              """
            |▼ Expect that Failure(java.lang.IllegalStateException: o noes):
            |  ✗ is success
            |    threw java.lang.IllegalStateException
              """.trimMargin()
            )
          }
        }
      }
    }
  }
}
