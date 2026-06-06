package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.MultipleFailuresError
import strikt.api.expectThat
import strikt.internal.opentest4j.MappingFailed

/**
 * Turns a list subject into various iterable types with the same content.
 */
private fun <E : Comparable<E>> List<E>.permute(): List<Iterable<E>> =
  listOf(
    this,
    toSet(),
    toSortedSet()
  )

/**
 * Turns a list subject with expected values into various iterable types with
 * the same content and the same expected value.
 */
private fun <E : Comparable<E>, EX> List<Pair<List<E>, EX>>.permuteExpected(): List<Pair<Iterable<E>, EX>> =
  flatMap {
    listOf(
      it.first to it.second,
      it.first.toSet() to it.second,
      it.first.toSortedSet() to it.second
    )
  }

internal class IterableAllAssertion {

  @Test
  fun `passes if all elements conform`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject).all {
          isLowerCase()
        }
      }
  }

  @Test
  fun `fails if any element does not conform`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).all {
            startsWith('c')
          }
        }
      }
  }
}

internal class IterableAllIndexedAssertion {

  @Test
  fun `passes if all elements conform`() {
    val subject = listOf("catflap-0", "rubberplant-1", "marzipan-2")
    expectThat(subject).allIndexed { index ->
      endsWith("-$index")
    }
  }

  @Test
  fun `fails if any element does not conform`() {
    val subject = listOf("catflap-1", "rubberplant-1", "marzipan-1")
    assertThrows<AssertionError> {
      expectThat(subject).allIndexed { index ->
        endsWith("-$index")
      }
    }
  }
}

internal class IterableAnyAssertion {

  @Test
  fun `passes if all elements conform`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject).any {
          isLowerCase()
        }
      }
  }

  @Test
  fun `passes if one element conforms`() {
    listOf("catflap", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        expectThat(subject).any {
          isLowerCase()
        }
      }
  }

  @Test
  fun `fails if no elements conform`() {
    listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).any {
            isLowerCase()
          }
        }
      }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).not().any {
      isUpperCase()
    }
  }
}

internal class IterableAnyIndexedAssertion {

  @Test
  fun `passes if all elements conform`() {
    val subject = listOf("catflap-0", "rubberplant-1", "marzipan-2")
    expectThat(subject).anyIndexed { index ->
      endsWith("-$index")
    }
  }

  @Test
  fun `passes if one element conforms`() {
    val subject = listOf("catflap-1", "rubberplant-1", "marzipan-1")
    expectThat(subject).anyIndexed { index ->
      endsWith("-$index")
    }
  }

  @Test
  fun `fails if no elements conform`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    assertThrows<AssertionError> {
      expectThat(subject).anyIndexed { index ->
        endsWith("-$index")
      }
    }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).not().anyIndexed { index ->
      endsWith("-$index")
    }
  }
}

internal class IterableNoneAssertion {

  @Test
  fun `passes if no elements conform`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject).none {
          isUpperCase()
        }
      }
  }

  @Test
  fun `fails if some elements conform`() {
    listOf("catflap", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).none {
            isUpperCase()
          }
        }
      }
  }

  @Test
  fun `fails if all elements conform`() {
    listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).none {
            isUpperCase()
          }
        }
      }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
    expectThat(subject).not().none {
      isUpperCase()
    }
  }
}

internal class IterableNoneIndexedAssertion {

  @Test
  fun `passes if no elements conform`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).noneIndexed { index ->
      endsWith("-$index")
    }
  }

  @Test
  fun `fails if some elements conform`() {
    val subject = listOf("catflap-1", "rubberplant-1", "marzipan-1")
    assertThrows<AssertionError> {
      expectThat(subject).noneIndexed { index ->
        endsWith("-$index")
      }
    }
  }

  @Test
  fun `fails if all elements conform`() {
    val subject = listOf("catflap-0", "rubberplant-1", "marzipan-2")
    assertThrows<AssertionError> {
      expectThat(subject).noneIndexed { index ->
        endsWith("-$index")
      }
    }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("catflap-1", "rubberplant-1", "marzipan-1")
    expectThat(subject).not().noneIndexed { index ->
      endsWith("-$index")
    }
  }
}

internal class IterableAtLeastAssertion {

  @Test
  fun `fails if not enough elements conform`() {
    listOf("catflap", "rubberplant", "MARZIPAN")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).atLeast(2) {
            isUpperCase()
          }
        }
      }
  }

  @Test
  fun `passes if exactly minimum amount of elements conforms`() {
    listOf("catflap", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        expectThat(subject).atLeast(2) {
          isUpperCase()
        }
      }
  }

  @Test
  fun `passes if all elements conform`() {
    listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        expectThat(subject).atLeast(2) {
          isUpperCase()
        }
      }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
    assertThrows<AssertionError> {
      expectThat(subject).not().atLeast(2) {
        isUpperCase()
      }
    }
  }
}

internal class IterableAtMostAssertion {

  @Test
  fun `passes if fewer elements conform`() {
    listOf("catflap", "rubberplant", "MARZIPAN")
      .permute()
      .forEach { subject ->
        expectThat(subject).atMost(2) {
          isUpperCase()
        }
      }
  }

  @Test
  fun `passes if exactly maximum amount of elements conform`() {
    listOf("catflap", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        expectThat(subject).atMost(2) {
          isUpperCase()
        }
      }
  }

  @Test
  fun `fails if too many elements conform`() {
    listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).atMost(2) {
            isUpperCase()
          }
        }
      }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
    expectThat(subject).not().atMost(2) {
      isUpperCase()
    }
  }
}

internal class IterableOneAssertion {

  @Test
  fun `passes if exactly one element conforms`() {
    listOf("catflap", "rubberplant", "MARZIPAN")
      .permute()
      .forEach { subject ->
        expectThat(subject).one {
          isUpperCase()
        }
      }
  }

  @Test
  fun `fails if too many elements conform`() {
    listOf("CATFLAP", "RUBBERPLANT", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).one {
            isUpperCase()
          }
        }
      }
  }

  @Test
  fun `fails if no elements conform`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<AssertionError> {
          expectThat(subject).one {
            isUpperCase()
          }
        }
      }
  }

  @Test
  fun `works with not`() {
    val subject = setOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
    expectThat(subject).not().one {
      isUpperCase()
    }
  }
}

internal class IterableContainsAssertion {

  @Test
  fun `passes if contains elements`() {
    listOf(
      listOf("catflap") to arrayOf("catflap"),
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap"),
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap", "marzipan")
    ).permuteExpected()
      .forEach { (subject, expected) ->
        expectThat(subject).contains(*expected)
      }
  }

  @Test
  fun `fails if does not contain elements`() {
    listOf(
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("fnord"),
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap", "fnord"),
      emptyList<String>() to arrayOf("catflap")
    ).permuteExpected()
      .forEach { (subject, expected) ->
        assertThrows<AssertionError> {
          expectThat(subject).contains(*expected)
        }
      }
  }

  @Test
  fun `any collection contains an empty list`() {
    expectThat(listOf("catflap", "rubberplant", "marzipan")).contains()
  }

  @Test
  fun `an empty collection contains an empty list`() {
    expectThat(emptyList<Any>()).contains()
  }

  @Test
  fun `has a nested failure for each missing element when there are multiple`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(listOf("catflap", "rubberplant", "marzipan"))
          .contains("fnord", "marzipan", "bojack")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains the elements ["fnord", "marzipan", "bojack"]
        |    ✗ contains "fnord"
        |    ✓ contains "marzipan"
        |    ✗ contains "bojack"
          """.trimMargin()
    )
  }

  @Test
  fun `does not nest failures when there is only one element`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(listOf("catflap", "rubberplant", "marzipan"))
          .contains("fnord")
      }
    expectThat(error.message).isEqualTo(
      "▼ Expect that [\"catflap\", \"rubberplant\", \"marzipan\"]:\n" +
        "  ✗ contains \"fnord\""
    )
  }
}

internal class IterableDoesNotContainAssertion {

  @Test
  fun `passes if subject is empty`() {
    emptyList<String>()
      .permute()
      .forEach { subject ->
        expectThat(subject)
          .doesNotContain("catflap", "rubberplant", "marzipan")
      }
  }

  @Test
  fun `passes if contains none of the elements`() {
    listOf(
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("fnord"),
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("xenocracy", "wye", "exercitation")
    ).permuteExpected()
      .forEach { (subject, elements) ->
        expectThat(subject)
          .doesNotContain(*elements)
      }
  }

  @Test
  fun `fails if no elements are specified for subject`() {
    listOf(
      emptyList(),
      listOf("catflap", "rubberplant", "marzipan")
    ).flatMap { it.permute() }
      .forEach { subject ->
        assertThrows<IllegalArgumentException> {
          expectThat(subject).doesNotContain()
        }
      }
  }

  @Test
  fun `fails if contains any of the elements`() {
    listOf(
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap"),
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap", "kakistocracy", "impeach"),
      listOf("catflap", "rubberplant", "marzipan") to arrayOf("owlbear", "marzipan", "illithid")
    )
      .permuteExpected()
      .forEach { (subject, elements) ->
        assertThrows<AssertionError> {
          expectThat(subject)
            .doesNotContain(*elements)
        }
      }
  }

  @Test
  fun `formats failure message correctly when there are multiple elements`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(listOf("catflap", "rubberplant", "marzipan"))
          .doesNotContain("catflap", "wye", "marzipan")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ does not contain any of the elements ["catflap", "wye", "marzipan"]
        |    ✗ does not contain "catflap"
        |    ✓ does not contain "wye"
        |    ✗ does not contain "marzipan"
          """.trimMargin()
    )
  }

  @Test
  fun `formats failure message correctly when there is a single element`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(listOf("catflap", "rubberplant", "marzipan"))
          .doesNotContain("catflap")
      }
    expectThat(error.message).isEqualTo(
      "▼ Expect that [\"catflap\", \"rubberplant\", \"marzipan\"]:\n" +
        "  ✗ does not contain \"catflap\""
    )
  }
}

internal class IterableContainsExactlyForSetAssertion {
  private val fixture = setOf("catflap", "rubberplant", "marzipan")

  @Test
  fun `passes if the elements are identical`() {
    expectThat(fixture)
      .containsExactly("catflap", "rubberplant", "marzipan")
  }

  @Test
  fun `fails if there are more elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture).containsExactly("rubberplant", "catflap")
    }
  }

  @Test
  fun `fails if there are fewer elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactly("catflap", "rubberplant", "marzipan", "fnord")
    }
  }

  @Test
  fun `fails if the order is different even though this is a Set`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactly("rubberplant", "catflap", "marzipan")
    }
  }
}

internal class IterableContainsExactlyForListAssertion {
  private val fixture = listOf("catflap", "rubberplant", "marzipan")

  @Test
  fun `passes if all the elements exist in the same order`() {
    expectThat(fixture)
      .containsExactly("catflap", "rubberplant", "marzipan")
  }

  @Test
  fun `fails if there are more elements than expected`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture).containsExactly("catflap", "rubberplant")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["catflap", "rubberplant"]
        |    ✓ contains "catflap"
        |    ✓ …at index 0
        |    ✓ contains "rubberplant"
        |    ✓ …at index 1
        |    ✗ contains no further elements
        |      found ["marzipan"]
          """.trimMargin()
    )
  }

  @Test
  fun `fails if there are fewer elements than expected`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture)
          .containsExactly("catflap", "rubberplant", "marzipan", "fnord")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["catflap", "rubberplant", "marzipan", "fnord"]
        |    ✓ contains "catflap"
        |    ✓ …at index 0
        |    ✓ contains "rubberplant"
        |    ✓ …at index 1
        |    ✓ contains "marzipan"
        |    ✓ …at index 2
        |    ✗ contains "fnord"
        |    ✓ contains no further elements
          """.trimMargin()
    )
  }

  /**
   * @see https://github.com/robfletcher/strikt/issues/159
   */
  @Test
  fun `fails if there are fewer elements than expected but the outlier is in the actual list`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactly("fnord", "catflap", "rubberplant", "marzipan")
    }
  }

  @Test
  fun `fails if the order is different`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture)
          .containsExactly("rubberplant", "catflap", "marzipan")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["rubberplant", "catflap", "marzipan"]
        |    ✓ contains "rubberplant"
        |    ✗ …at index 0
        |      found "catflap"
        |    ✓ contains "catflap"
        |    ✗ …at index 1
        |      found "rubberplant"
        |    ✓ contains "marzipan"
        |    ✓ …at index 2
        |    ✓ contains no further elements
          """.trimMargin()
    )
  }

  @Test
  fun `fails if the cardinality of an element is lower than expected`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture)
          .containsExactly("catflap", "rubberplant", "marzipan", "marzipan")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["catflap", "rubberplant", "marzipan", "marzipan"]
        |    ✓ contains "catflap"
        |    ✓ …at index 0
        |    ✓ contains "rubberplant"
        |    ✓ …at index 1
        |    ✓ contains "marzipan"
        |    ✓ …at index 2
        |    ✗ contains "marzipan"
        |    ✓ contains no further elements
          """.trimMargin()
    )
  }
}

internal class IterableContainsExactlyNonCollectionIterableAssertion {
  private val fixture =
    object : Iterable<String> {
      override fun iterator() = arrayOf("catflap", "rubberplant", "marzipan").iterator()
    }

  @Test
  fun `passes if the elements are identical`() {
    expectThat(fixture)
      .describedAs("a non-Collection iterable %s")
      .containsExactly("catflap", "rubberplant", "marzipan")
  }

  @Test
  fun `fails if the elements are ordered differently`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .describedAs("a non-Collection iterable %s")
        .containsExactly("marzipan", "rubberplant", "catflap")
    }
  }

  @Test
  fun `fails if there are more elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .describedAs("a non-Collection iterable %s")
        .containsExactly("catflap", "rubberplant")
    }
  }

  @Test
  fun `fails if there are fewer elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .describedAs("a non-Collection iterable %s")
        .containsExactly("catflap", "rubberplant", "marzipan", "fnord")
    }
  }

  @Test
  fun `fails if the cardinality of an element is lower than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .describedAs("a non-Collection iterable %s")
        .containsExactly("catflap", "rubberplant", "marzipan", "marzipan")
    }
  }

  @Test
  fun `fails if it's supposed to be empty and isn't`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .describedAs("a non-Collection iterable %s")
        .containsExactly()
    }
  }

  @Test
  fun `passes if it's supposed to be empty and is`() {
    val emptySubject =
      object : Iterable<String> {
        override fun iterator() = emptySequence<String>().iterator()
      }
    expectThat(emptySubject).containsExactly()
  }
}

internal class IterableContainsExactlyInAnyOrderASetAssertion {
  private val fixture = setOf("catflap", "rubberplant", "marzipan")

  @Test
  fun `passes if the elements are identical`() {
    expectThat(fixture)
      .containsExactlyInAnyOrder("rubberplant", "catflap", "marzipan")
  }

  @Test
  fun `fails if there are more elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactlyInAnyOrder("rubberplant", "catflap")
    }
  }

  @Test
  fun `fails if there are fewer elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactlyInAnyOrder(
          "catflap",
          "rubberplant",
          "marzipan",
          "fnord"
        )
    }
  }
}

internal class IterableContainsExactlyInAnyOrderAListAssertion {
  private val fixture = listOf("catflap", "rubberplant", "marzipan")

  @Test
  fun `passes if all the elements exist in the same order`() {
    expectThat(fixture)
      .containsExactlyInAnyOrder("catflap", "rubberplant", "marzipan")
  }

  @Test
  fun `fails if there are more elements than expected`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture)
          .containsExactlyInAnyOrder("catflap", "rubberplant")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["catflap", "rubberplant"] in any order
        |    ✓ contains "catflap"
        |    ✓ contains "rubberplant"
        |    ✗ contains no further elements
        |      found ["marzipan"]
          """.trimMargin()
    )
  }

  @Test
  fun `fails if the cardinality of an element is lower than expected`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture)
          .containsExactlyInAnyOrder(
            "catflap",
            "rubberplant",
            "marzipan",
            "marzipan"
          )
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["catflap", "rubberplant", "marzipan", "marzipan"] in any order
        |    ✓ contains "catflap"
        |    ✓ contains "rubberplant"
        |    ✓ contains "marzipan"
        |    ✗ contains "marzipan"
        |    ✓ contains no further elements
          """.trimMargin()
    )
  }

  @Test
  fun `fails if there are fewer elements than expected`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(fixture)
          .containsExactlyInAnyOrder(
            "catflap",
            "rubberplant",
            "marzipan",
            "fnord"
          )
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ["catflap", "rubberplant", "marzipan"]:
        |  ✗ contains exactly the elements ["catflap", "rubberplant", "marzipan", "fnord"] in any order
        |    ✓ contains "catflap"
        |    ✓ contains "rubberplant"
        |    ✓ contains "marzipan"
        |    ✗ contains "fnord"
        |    ✓ contains no further elements
          """.trimMargin()
    )
  }

  @Test
  fun `passes if the order is different`() {
    expectThat(fixture)
      .containsExactlyInAnyOrder("rubberplant", "catflap", "marzipan")
  }
}

internal class IterableContainsExactlyInAnyOrderNonCollectionIterableAssertion {
  private val fixture =
    object : Iterable<String> {
      override fun iterator() = arrayOf("catflap", "rubberplant", "marzipan").iterator()
    }

  @Test
  fun `passes if the elements are identical`() {
    expectThat(fixture)
      .containsExactlyInAnyOrder("catflap", "rubberplant", "marzipan")
  }

  @Test
  fun `passes if the elements are ordered differently`() {
    expectThat(fixture)
      .containsExactlyInAnyOrder("marzipan", "rubberplant", "catflap")
  }

  @Test
  fun `fails if there are more elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactlyInAnyOrder("catflap", "rubberplant")
    }
  }

  @Test
  fun `fails if there are fewer elements than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactlyInAnyOrder(
          "catflap",
          "rubberplant",
          "marzipan",
          "fnord"
        )
    }
  }

  @Test
  fun `fails if the cardinality of an element is lower than expected`() {
    assertThrows<AssertionError> {
      expectThat(fixture)
        .containsExactlyInAnyOrder(
          "catflap",
          "rubberplant",
          "marzipan",
          "marzipan"
        )
    }
  }

  @Test
  fun `fails if it's supposed to be empty and isn't`() {
    assertThrows<AssertionError> {
      expectThat(fixture).containsExactlyInAnyOrder()
    }
  }

  @Test
  fun `passes if it's supposed to be empty and is`() {
    val emptySubject =
      object : Iterable<String> {
        override fun iterator() = emptySequence<String>().iterator()
      }
    expectThat(emptySubject).containsExactlyInAnyOrder()
  }
}

internal class IterableWithFirstFunction {

  @Test
  fun `withFirst function runs assertions on the first element`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject).withFirst {
          isEqualTo("catflap")
        }
      }
  }

  @Test
  fun `withFirst function fails if the nested assertions fail on the first element`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<MultipleFailuresError> {
          expectThat(subject).withFirst {
            isEqualTo("rubberplant")
          }
        }
      }
  }
}

internal class IterableWithFirstPredicateFunction {

  @Test
  fun `withFirst(predicate) function runs assertions on the first element that matches the predicate`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject).withFirst({ it.startsWith('r') }) {
          isEqualTo("rubberplant")
        }
      }
  }

  @Test
  fun `withFirst(predicate) function fails if the nested assertions fail on the first element that matches the predicate`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<MultipleFailuresError> {
          expectThat(subject).withFirst({ it.startsWith('r') }) {
            isEqualTo("catflap")
          }
        }
      }
  }

  @Test
  fun `withFirst(predicate) function fails if nothing matches the predicate`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<MappingFailed> {
          expectThat(subject).withFirst({ it.startsWith('z') }) {
            isEqualTo("catflap")
          }
        }
      }
  }
}

internal class IterableWithLastFunction {

  @Test
  fun `withLast function runs assertions on the last element`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject.sorted()).withLast {
          isEqualTo("rubberplant")
        }
      }
  }

  @Test
  fun `withLast function fails if the nested assertions fail on the last element`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<MultipleFailuresError> {
          expectThat(subject.sorted()).withLast {
            isEqualTo("marzipan")
          }
        }
      }
  }
}

internal class IterableWithElementAtFunction {

  @Test
  fun `withElementAt function runs assertions on the nth element`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        expectThat(subject.sorted()).withElementAt(1) {
          isEqualTo("marzipan")
        }
      }
  }

  @Test
  fun `withElementAt function fails if the nested assertions fail on the nth element`() {
    listOf("catflap", "rubberplant", "marzipan")
      .permute()
      .forEach { subject ->
        assertThrows<MultipleFailuresError> {
          expectThat(subject.sorted()).withElementAt(1) {
            isEqualTo("catflap")
          }
        }
      }
  }
}

internal class IterableWithSingleFunction {

  @Test
  fun `withSingle function runs assertions on the single element of a collection`() {
    expectThat(listOf("catflap")).withSingle {
      isEqualTo("catflap")
    }
  }

  @Test
  fun `withSingle function fails if the nested assertions fail on the single element`() {
    assertThrows<MultipleFailuresError> {
      expectThat(listOf("catflap")).withSingle {
        isEqualTo("rubberplant")
      }
    }
  }

  @Test
  fun `withSingle function fails if a collection is empty`() {
    assertThrows<MappingFailed> {
      expectThat(emptyList<String>()).withSingle {
        isEqualTo("rubberplant")
      }
    }
  }

  @Test
  fun `withSingle function fails if a collection contains more than one element`() {
    assertThrows<MappingFailed> {
      expectThat(listOf("catflap", "rubberplant", "marzipan")).withSingle {
        isEqualTo("rubberplant")
      }
    }
  }
}

internal class IterableCountMapping {

  @Test
  fun `maps to an assertion on the iterable's size`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).count().isEqualTo(subject.size)
  }

  @Test
  fun `maps to an assertion on the count of elements matching the predicate`() {
    val subject = listOf("catflap", "rubberplant", "marzipan")
    expectThat(subject)
      .count("elements containing 't'") { it.contains("t") }
      .isEqualTo(2)
  }
}

internal class IterableIsSortedWithComparatorAssertion {

  @Test
  fun `passes if the subject is empty`() {
    expectThat(emptyList<Any?>())
      .isSorted(Comparator.comparingInt { it.hashCode() })
  }

  @Test
  fun `fails in a block assertion if the subject is not in order`() {
    assertThrows<AssertionError> {
      expectThat(listOf(1, 3, 2)) {
        isSorted(Comparator.naturalOrder())
      }
    }
  }

  @Test
  fun `fails in a chained assertion if the subject is not in order`() {
    assertThrows<AssertionError> {
      expectThat(listOf(1, 3, 2))
        .isSorted(Comparator.naturalOrder())
    }
  }

  @Test
  fun `passes if the subject is in order`() {
    expectThat(listOf("catflap", "marzipan", "rubberplant"))
      .isSorted(Comparator.naturalOrder<String>())
  }

  @Test
  fun `fails if the subject is not sorted according to the comparator`() {
    assertThrows<AssertionError> {
      expectThat(listOf("catflap", "rubberplant", "marzipan"))
        .isSorted(Comparator.naturalOrder<String>().reversed())
    }
  }

  @Test
  fun `passes if the Null value is handled through the Comparator instance`() {
    expectThat(listOf("catflap", "marzipan", null))
      .isSorted(Comparator.nullsLast(Comparator.naturalOrder<String>()))
  }

  @Test
  fun `fails with NPE if the Null value isn't handled through the Comparator instance`() {
    assertThrows<NullPointerException> {
      expectThat(listOf("catflap", "marzipan", null))
        .isSorted(Comparator.naturalOrder<String>())
    }
  }
}

internal class IterableIsSortedOnNonComparableAssertion {
  private val subject =
    expectThat(
      listOf(
        listOf("catflap"),
        listOf("marzipan", "persipan"),
        listOf("rubberplant", "rubber bush", "rubber tree")
      )
    )

  @Test
  fun `passes if the subject is sorted`() {
    subject.isSorted(Comparator.comparing { it?.size ?: 0 })
  }

  @Test
  fun `fails if the subject is not sorted according to the comparator`() {
    assertThrows<AssertionError> {
      subject.isSorted(Comparator.comparing(Collection<String>::size).reversed())
    }
  }
}

internal class IterableIsSortedAssertion {

  @Test
  fun `passes if the subject is empty`() {
    expectThat(emptyList<String>()).isSorted()
  }

  @Test
  fun `passes if the elements are in natural order`() {
    expectThat(listOf("catflap", "marzipan", "rubberplant"))
      .isSorted()
  }

  @Test
  fun `fails if the elements are not in natural order`() {
    assertThrows<AssertionError> {
      expectThat(listOf("catflap", "rubberplant", "marzipan"))
        .isSorted()
    }
  }
}
