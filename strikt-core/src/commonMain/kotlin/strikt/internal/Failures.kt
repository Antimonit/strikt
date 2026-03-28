package strikt.internal

import strikt.api.Status.Failed

internal expect fun createAssertionFailure(message: String, failed: Failed?): Throwable

internal expect fun throwCompoundAssertionFailure(message: String, failures: List<Throwable>): Nothing

internal expect fun throwIncompleteAssertion(): Nothing

internal expect fun throwMappingFailed(description: String, cause: Throwable): Nothing
