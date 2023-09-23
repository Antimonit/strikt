package strikt.internal

import strikt.api.Assertion
import strikt.api.DescribeableBuilder
import strikt.api.ExpectationBuilder
import strikt.internal.AssertionStrategy.Collecting
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class DefaultExpectationBuilder(
  private val subjects: MutableList<AssertionSubject<*>>
) : ExpectationBuilder {
  override fun <T> that(subject: T): DescribeableBuilder<T> =
    AssertionSubject(subject)
      .also { subjects.add(it) }
      .let {
        AssertionBuilder(it, Collecting)
      }

  override fun <T> that(
    subject: T,
    block: Assertion.Builder<T>.() -> Unit
  ): DescribeableBuilder<T> = that(subject).apply(block)

  override suspend fun <T> coCatching(
    action: suspend () -> T
  ): DescribeableBuilder<Result<T>> = achromaticCatching { action() }

  override fun <T> catching(
    action: () -> T
  ): DescribeableBuilder<Result<T>> = achromaticCatching { action() }

  private inline fun <T> achromaticCatching(
    action: () -> T
  ): DescribeableBuilder<Result<T>> =
    that(
      try {
        action()
          .let(::success)
      } catch (e: Throwable) {
        failure(e)
      }
    )
}
