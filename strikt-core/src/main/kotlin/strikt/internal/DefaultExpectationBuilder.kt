package strikt.internal

import strikt.api.Assertion
import strikt.api.DescribeableBuilder
import strikt.api.ExpectationBuilder
import strikt.internal.AssertionStrategy.Collecting

@PublishedApi
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
}
