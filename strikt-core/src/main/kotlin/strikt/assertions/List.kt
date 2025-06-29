package strikt.assertions

import strikt.api.Assertion.Builder

/**
 * Maps this assertion to an assertion on the element at index [i] in the
 * subject list.
 */
operator fun <T : List<E>, E> Builder<T>.get(i: Int): Builder<E> = get("element [$i] %s") { this[i] }

/**
 * Maps this assertion to an assertion on the elements at the sub-list
 * represented by [range] in the subject list.
 */
operator fun <T : List<E>, E> Builder<T>.get(range: IntRange): Builder<List<E>> =
  get("elements [$range] %s") {
    subList(range.first, range.last + 1)
  }

/**
 * Asserts that all [elements] are present in the subject in exactly the same order
 */
fun <T : List<E>, E> Builder<T>.containsSequence(vararg elements: E) = containsSequence(elements.toList())

/**
 * Asserts that all [elements] are present in the subject in exactly the same order
 */
fun <T : List<E>, E> Builder<T>.containsSequence(elements: List<E>) =
  assert("contains the sequence: %s in exactly the same order", elements.toList()) { subject ->
    val subjectList = subject.toList()
    val expectedList = elements.toList()

    when {
      subjectList.isEmpty() -> fail("subject cannot be empty")
      expectedList.isEmpty() -> fail("expected sequence cannot empty")
      subjectList.size < expectedList.size -> fail("expected sequence cannot be longer than subject")
      subjectList.windowed(expectedList.size).any { it == expectedList } -> pass()
      else -> fail()
    }
  }
