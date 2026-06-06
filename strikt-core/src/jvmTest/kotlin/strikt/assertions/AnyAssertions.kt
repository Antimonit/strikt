package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.Assertion.Builder
import strikt.api.expectThat
import kotlin.time.Instant

internal class AnyAssertions {

  @Test
  fun `isNull passes when the subject is null`() {
    expectThat<Any?>(null).isNull()
  }

  @Test
  fun `isNull down-casts the subject`() {
    @Suppress("USELESS_IS_CHECK")
    expectThat<Any?>(null)
      .also { assert(it is Builder<Any?>) }
      .isNull()
      .also { assert(it is Builder<Nothing>) }
  }

  @Test
  fun `isNull fails when the subject is not null`() {
    listOf("fnord", 1L, "null").forEach<Any?> { value ->
      assertThrows<AssertionFailedError> {
        expectThat(value).isNull()
      }
    }
  }

  @Test
  fun `isNotNull fails when the subject is null`() {
    assertThrows<AssertionFailedError> {
      expectThat(null).isNotNull()
    }
  }

  @Test
  fun `isNotNull passes when the subject is not null`() {
    listOf("fnord", 1L, "null").forEach<Any?> { value ->
      expectThat(value).isNotNull()
    }
  }

  @Test
  fun `isNotNull narrows the subject type`() {
    listOf("fnord", 1L, "null").forEach<Any?> { value ->
      @Suppress("USELESS_IS_CHECK")
      expectThat(value)
        .also { assert(it is Builder<Any?>) }
        .isNotNull()
        .also { assert(it is Builder<Any>) }
    }
  }

  @Test
  fun `withNotNull fails when the subject is null`() {
    assertThrows<AssertionFailedError> {
      expectThat<Any?>(null).withNotNull {
        isEqualTo("fnord")
      }
    }
  }

  @Test
  fun `withNotNull passes when the subject is not null`() {
    listOf("fnord", 1L, "null").forEach<Any?> { value ->
      expectThat(value).withNotNull {
        isEqualTo(value)
      }
    }
  }

  @Test
  fun `isA fails when the subject is null`() {
    assertThrows<AssertionFailedError> {
      expectThat(null).isA<String>()
    }
  }

  @Test
  fun `isA fails when the subject is of a different type`() {
    assertThrows<AssertionFailedError> {
      expectThat(1L).isA<String>()
    }
  }

  @Test
  fun `isA passes when the subject is of the expected type`() {
    expectThat<Any?>("fnord").isA<String>()
  }

  @Test
  fun `isA narrows the subject type`() {
    @Suppress("USELESS_IS_CHECK")
    expectThat<Any?>("fnord")
      .also { assert(it is Builder<Any?>) }
      .isA<String>()
      .also { assert(it is Builder<String>) }
  }

  @Test
  fun `isA narrows the type to enable specialized assertions`() {
    expectThat<Any?>("fnord").isA<String>().hasLength(5) // only available on Assertion<CharSequence>
  }

  @Test
  fun `isA passes when the subject is a subtype`() {
    expectThat<Any?>(1L).isA<Number>()
  }

  @Test
  fun `isA narrows the subject type transitively`() {
    @Suppress("USELESS_IS_CHECK")
    expectThat<Any?>(1L)
      .also { assert(it is Builder<Any?>) }
      .isA<Number>()
      .also { assert(it is Builder<Number>) }
      .isA<Long>()
      .also { assert(it is Builder<Long>) }
  }

  @Test
  fun `isEqualTo passes when the subject equals the expected value`() {
    expectThat("fnord").isEqualTo("fnord")
  }

  @Test
  fun `isEqualTo fails when the values are not equal`() {
    listOf(
      "fnord" to "FNORD",
      1 to 1L,
      null to "fnord",
      "fnord" to null
    ).forEach { (subject, expected) ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isEqualTo(expected)
      }
    }
  }

  @Test
  fun `isEqualTo failure message specifies the types when values look the same`() {
    val error = assertThrows<AssertionFailedError> {
      expectThat<Number>(5L).isEqualTo(5)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that 5:
      |  ✗ is equal to 5 (Int)
      |          found 5 (Long)
      """.trimMargin()
    )
  }

  @Test
  fun `isNotEqualTo fails when the subject equals the expected value`() {
    assertThrows<AssertionFailedError> {
      expectThat("fnord").isNotEqualTo("fnord")
    }
  }

  @Test
  fun `isNotEqualTo passes when the values are not equal`() {
    listOf(
      "fnord" to "FNORD",
      1 to 1L,
      null to "fnord",
      "fnord" to null
    ).forEach { (subject, expected) ->
      expectThat(subject).isNotEqualTo(expected)
    }
  }

  @Test
  fun `isSameInstanceAs fails when the subject and expected values are different instances`() {
    listOf(
      listOf("fnord") to listOf("fnord"),
      null to listOf("fnord"),
      listOf("fnord") to null,
      1 to 1L,
      Instant.parse("2026-12-31T12:30:00Z").let { it to Instant.fromEpochMilliseconds(it.toEpochMilliseconds()) }
    ).forEach { (subject, expected) ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isSameInstanceAs(expected)
      }
    }
  }

  @Test
  fun `isSameInstanceAs passes when the subject and expected values are the same instance`() {
    listOf("fnord", 1L, null, listOf("fnord"), Instant.parse("2026-12-31T12:30:00Z"))
      .map { it to it }
      .forEach { (subject, expected) ->
        expectThat(subject).isSameInstanceAs(expected)
      }
  }

  @Test
  fun `isNotSameInstanceAs passes when the subject and expected values are different instances`() {
    listOf(
      listOf("fnord") to listOf("fnord"),
      null to listOf("fnord"),
      listOf("fnord") to null,
      1 to 1L,
      Instant.parse("2026-12-31T12:30:00Z").let { it to Instant.fromEpochMilliseconds(it.toEpochMilliseconds()) }
    ).forEach { (subject, expected) ->
      expectThat(subject).isNotSameInstanceAs(expected)
    }
  }

  @Test
  fun `isNotSameInstanceAs fails when the subject and expected values are the same instance`() {
    listOf("fnord", 1L, null, listOf("fnord"), Instant.fromEpochMilliseconds(0))
      .map { it to it }
      .forEach { (subject, expected) ->
        assertThrows<AssertionFailedError> {
          expectThat(subject).isNotSameInstanceAs(expected)
        }
      }
  }

  @Test
  fun `isContainedIn fails when the subject is not in the collection`() {
    assertThrows<AssertionFailedError> {
      expectThat("fnord").isContainedIn(listOf("catflap", "rubberplant", "marzipan"))
    }
  }

  @Test
  fun `isContainedIn passes when the subject is in the collection`() {
    expectThat("fnord").isContainedIn(listOf("catflap", "rubberplant", "marzipan", "fnord"))
  }

  @Test
  fun `isContainedIn passes when the subject is a subtype of the collection element type`() {
    // this is really just testing compilation works
    expectThat<Number>(1).isContainedIn(listOf(1, 1L, 1.0, 1.0f))
  }
}
