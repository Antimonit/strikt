package strikt

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

  @Test
  fun `isSuccess maps to the action's returned value`() {
    expectCatching { "kthxbye" }
      .isSuccess()
      .isA<String>()
      .isEqualTo("kthxbye")
  }

  @Test
  fun `isFailure fails when the action succeeded`() {
    assertThrows<AssertionFailedError> {
      expectCatching { "kthxbye" }.isFailure()
    }
  }

  @Test
  fun `isSuccess chains correctly in a block`() {
    assertThrows<AssertionError> {
      expectCatching { "kthxbye" }.and {
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

  @Test
  fun `isFailure maps to the exception thrown by the action`() {
    expectCatching { error("o noes") }
      .isFailure()
      .isA<IllegalStateException>()
      .message
      .isEqualTo("o noes")
  }

  @Test
  fun `isSuccess fails when the action failed`() {
    assertThrows<AssertionFailedError> {
      expectCatching { error("o noes") }.isSuccess()
    }
  }

  @Test
  fun `isFailure chains correctly in a block`() {
    assertThrows<AssertionError> {
      expectCatching { error("o noes") }.and {
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
