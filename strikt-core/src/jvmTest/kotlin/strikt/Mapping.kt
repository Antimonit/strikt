package strikt

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.containsExactly
import strikt.assertions.elementAt
import strikt.assertions.filter
import strikt.assertions.filterIsInstance
import strikt.assertions.filterNot
import strikt.assertions.first
import strikt.assertions.flatMap
import strikt.assertions.get
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.last
import strikt.assertions.map
import strikt.assertions.message
import strikt.assertions.single
import java.time.LocalDate

@DisplayName("mapping assertions")
internal class Mapping {
  @Test
  fun `map() on iterable subjects maps to an iterable`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject)
      .map { it.uppercase() }
      .containsExactly("CATFLAP", "RUBBERPLANT", "MARZIPAN")
  }

  @Test
  fun `first() maps to the first element of an iterable`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).first().isEqualTo("catflap")
  }

  @Test
  fun `elementAt maps an iterable to the indexed element`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).elementAt(1).isEqualTo("rubberplant")
  }

  @Test
  fun `single() maps to the single element of an iterable`() {
    val subject = listOf("catflap")
    expectThat(subject).single().isEqualTo("catflap")
  }

  @Test
  fun `single() fails when the iterable has no elements`() {
    val subject = emptyList<String>()
    assertThrows<AssertionError> {
      expectThat(subject).single().isEqualTo("catflap")
    }.let { error ->
      expectThat(error).message.isEqualTo(
        """▼ Expect that []:
          |  ✗ has only one element
          |    found []"""
          .trimMargin()
      )
    }
  }

  @Test
  fun `single() fails when the iterable has multiple elements`() {
    val subject = listOf("catflap", "rubberplant")
    assertThrows<AssertionError> {
      expectThat(subject).single().isEqualTo("catflap")
    }.let { error ->
      expectThat(error).message.isEqualTo(
        """▼ Expect that ["catflap", "rubberplant"]:
          |  ✗ has only one element
          |    found ["catflap", "rubberplant"]"""
          .trimMargin()
      )
    }
  }

  @Test
  fun `last() maps to the last element of an iterable`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).last().isEqualTo("marzipan")
  }

  @Test
  fun `array access maps to an indexed element of a list`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject)[1].isEqualTo("rubberplant")
  }

  @Test
  fun `array access maps to a sub-list of a list`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject)[1..2].containsExactly("rubberplant", "marzipan")
  }

  @Test
  fun `array access maps to a value of a map`() {
    val subject = mapOf("foo" to "bar")
    expectThat(subject)["foo"].isNotNull().isEqualTo("bar")
    expectThat(subject)["bar"].isNull()
  }

  @Test
  fun `filter reduces a subject iterable`() {
    val subject = listOf(1, 2, 3, 4)
    expectThat(subject)
      .filter { it >= 2 }
      .containsExactly(2, 3, 4)
  }

  @Test
  fun `filterNot reduces a subject iterable`() {
    val subject = listOf(1, 2, 3, 4)
    expectThat(subject)
      .filterNot { it > 2 }
      .containsExactly(1, 2)
  }

  @Test
  fun `filterIsInstance reduces a subject iterable`() {
    val subject = listOf(1, 2L, 3.0, 4)
    expectThat(subject)
      .filterIsInstance<Int>()
      .containsExactly(1, 4)
  }

  @Test
  fun `message maps to an exception message`() {
    expectThrows<IllegalStateException> {
      check(false) { "o noes" }
    }
      .message
      .isEqualTo("o noes")
  }

  @Test
  fun `first maps an iterable to its first element`() {
    val subject = listOf("catflap", "rubberplant", "marzipan", "radish")
    expectThat(subject)
      .first { it.startsWith("r") }
      .isEqualTo("rubberplant")
  }

  @Test
  fun `flatMap maps a result iterable to a flattened iterable`() {
    val subject =
      listOf(
        mapOf("words" to listOf("catflap", "rubberplant", "marzipan")),
        mapOf("words" to listOf("kattenluik", "rubberboom", "marsepein"))
      )
    expectThat(subject)
      .flatMap { it["words"]!! }
      .containsExactly(
        "catflap",
        "rubberplant",
        "marzipan",
        "kattenluik",
        "rubberboom",
        "marsepein"
      )
  }
}

data class Person(val name: String, val birthDate: LocalDate = LocalDate.now())

data class Album(val name: String)
