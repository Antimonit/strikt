package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

internal class ComparableAssertions {

  private val instant = Instant.parse("2026-12-31T12:30:00Z")

  @Test
  fun `isGreaterThan passes when the subject is greater than the expected value`() {
    expectThat(1).isGreaterThan(0)
    expectThat(instant).isGreaterThan(instant - 1.milliseconds)
    expectThat("a").isGreaterThan("A")
  }

  @Test
  fun `isGreaterThan fails when the subject is equal to the expected value`() {
    assertThrows<AssertionError> { expectThat(1).isGreaterThan(1) }
    assertThrows<AssertionError> { expectThat(instant).isGreaterThan(instant) }
    assertThrows<AssertionError> { expectThat("a").isGreaterThan("a") }
  }

  @Test
  fun `isGreaterThan fails when the subject is less than the expected value`() {
    assertThrows<AssertionError> { expectThat(1).isGreaterThan(2) }
    assertThrows<AssertionError> { expectThat(instant).isGreaterThan(instant + 1.milliseconds) }
    assertThrows<AssertionError> { expectThat("a").isGreaterThan("z") }
  }

  @Test
  fun `isGreaterThanOrEqualTo passes when the subject is greater than the expected value`() {
    expectThat(1).isGreaterThanOrEqualTo(0)
    expectThat(instant).isGreaterThanOrEqualTo(instant - 1.milliseconds)
    expectThat("a").isGreaterThanOrEqualTo("A")
  }

  @Test
  fun `isGreaterThanOrEqualTo passes when the subject is equal to the expected value`() {
    expectThat(1).isGreaterThanOrEqualTo(1)
    expectThat(instant).isGreaterThanOrEqualTo(instant)
    expectThat("a").isGreaterThanOrEqualTo("a")
  }

  @Test
  fun `isGreaterThanOrEqualTo fails when the subject is less than the expected value`() {
    assertThrows<AssertionError> { expectThat(1).isGreaterThanOrEqualTo(2) }
    assertThrows<AssertionError> { expectThat(instant).isGreaterThanOrEqualTo(instant + 1.milliseconds) }
    assertThrows<AssertionError> { expectThat("a").isGreaterThanOrEqualTo("z") }
  }

  @Test
  fun `isLessThan fails when the subject is greater than the expected value`() {
    assertThrows<AssertionError> { expectThat(1).isLessThan(0) }
    assertThrows<AssertionError> { expectThat(instant).isLessThan(instant - 1.milliseconds) }
    assertThrows<AssertionError> { expectThat("a").isLessThan("A") }
  }

  @Test
  fun `isLessThan fails when the subject is equal to the expected value`() {
    assertThrows<AssertionError> { expectThat(1).isLessThan(1) }
    assertThrows<AssertionError> { expectThat(instant).isLessThan(instant) }
    assertThrows<AssertionError> { expectThat("a").isLessThan("a") }
  }

  @Test
  fun `isLessThan passes when the subject is less than the expected value`() {
    expectThat(1).isLessThan(2)
    expectThat(instant).isLessThan(instant + 1.milliseconds)
    expectThat("a").isLessThan("z")
  }

  @Test
  fun `isLessThanOrEqualTo fails when the subject is greater than the expected value`() {
    assertThrows<AssertionError> { expectThat(1).isLessThanOrEqualTo(0) }
    assertThrows<AssertionError> { expectThat(instant).isLessThanOrEqualTo(instant - 1.milliseconds) }
    assertThrows<AssertionError> { expectThat("a").isLessThanOrEqualTo("A") }
  }

  @Test
  fun `isLessThanOrEqualTo passes when the subject is equal to the expected value`() {
    expectThat(1).isLessThanOrEqualTo(1)
    expectThat(instant).isLessThanOrEqualTo(instant)
    expectThat("a").isLessThanOrEqualTo("a")
  }

  @Test
  fun `isLessThanOrEqualTo passes when the subject is less than the expected value`() {
    expectThat(1).isLessThanOrEqualTo(2)
    expectThat(instant).isLessThanOrEqualTo(instant + 1.milliseconds)
    expectThat("a").isLessThanOrEqualTo("z")
  }

  @Test
  fun `comparesEqualTo fails when the subject is greater than the expected value`() {
    assertThrows<AssertionError> { expectThat(1).comparesEqualTo(0) }
    assertThrows<AssertionError> { expectThat(instant).comparesEqualTo(instant - 1.milliseconds) }
    assertThrows<AssertionError> { expectThat("a").comparesEqualTo("A") }
  }

  @Test
  fun `comparesEqualTo fails when the subject is less than the expected value`() {
    assertThrows<AssertionError> { expectThat(1).comparesEqualTo(2) }
    assertThrows<AssertionError> { expectThat(instant).comparesEqualTo(instant + 1.milliseconds) }
    assertThrows<AssertionError> { expectThat("a").comparesEqualTo("z") }
  }

  @Test
  fun `comparesEqualTo passes when the subject is equal to the expected value`() {
    expectThat(1).comparesEqualTo(1)
    expectThat(instant).comparesEqualTo(instant)
    expectThat("a").comparesEqualTo("a")
  }

  @Test
  fun `isIn passes when the Int value is in the range`() {
    (1..10).forEach { i ->
      expectThat(i).isIn(1..10)
    }
  }

  @Test
  fun `isIn fails when the Int value is out of the range`() {
    ((-5..0) + (11..15)).forEach { i ->
      assertThrows<AssertionError> {
        expectThat(i).isIn(1..10)
      }
    }
  }

  @Test
  fun `isIn passes when the Long value is in the range`() {
    (1L..10L).forEach { i ->
      expectThat(i).isIn(1L..10L)
    }
  }

  @Test
  fun `isIn fails when the Long value is out of the range`() {
    ((-5L..0L) + (11L..15L)).forEach { i ->
      assertThrows<AssertionError> {
        expectThat(i).isIn(1L..10L)
      }
    }
  }
}
