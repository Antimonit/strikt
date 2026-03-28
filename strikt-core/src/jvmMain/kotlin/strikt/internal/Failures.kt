package strikt.internal

import org.opentest4j.AssertionFailedError
import strikt.api.Status.Failed
import strikt.internal.opentest4j.AssertionFailed
import strikt.internal.opentest4j.CompoundAssertionFailure
import strikt.internal.opentest4j.IncompleteAssertion
import strikt.internal.opentest4j.MappingFailed

internal actual fun createAssertionFailure(message: String, failed: Failed?): Throwable {
  val error =
    if (failed?.comparison != null) {
      AssertionFailed(
        message,
        failed.comparison.expected,
        failed.comparison.actual,
        failed.cause
      )
    } else {
      AssertionFailed(
        message,
        failed?.cause
      )
    }

  val stackTrace = error.stackTrace
  val lastIndex =
    stackTrace
      .indexOfLast { it.className.startsWith("strikt") }
  val suppressedElements = stackTrace.copyOfRange(0, lastIndex)
  val remainingElements = stackTrace.copyOfRange(lastIndex + 1, stackTrace.lastIndex)
  error.stackTrace = remainingElements
  val striktError = AssertionFailedError()
  striktError.stackTrace = suppressedElements
  error.addSuppressed(striktError)
  return error
}

internal actual fun throwCompoundAssertionFailure(message: String, failures: List<Throwable>): Nothing =
  throw CompoundAssertionFailure(message, failures)

internal actual fun throwIncompleteAssertion(): Nothing =
  throw IncompleteAssertion()

internal actual fun throwMappingFailed(description: String, cause: Throwable): Nothing =
  throw MappingFailed(description, cause)
