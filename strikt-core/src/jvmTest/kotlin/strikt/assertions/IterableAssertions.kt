package strikt.assertions

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.opentest4j.MultipleFailuresError
import strikt.api.expectThat
import strikt.internal.opentest4j.MappingFailed

internal class IterableAssertions {
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

      @Nested
      inner class AllAssertion {
        @TestFactory
        fun `all assertion passes if all elements`() {
          listOf("catflap", "rubberplant", "marzipan")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("a ${subject.javaClass.simpleName} conform") {
                expectThat(subject).all {
                  isLowerCase()
                }
              }
            }
        }

        @TestFactory
        fun `all assertion fails if any element`() {
          listOf("catflap", "rubberplant", "marzipan")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("of a ${subject.javaClass.simpleName} does not conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).all {
                    startsWith('c')
                  }
                }
              }
            }
        }
      }

        @Nested
        inner class AllIndexedAssertion {
          @Test
          fun `passes if all elements of a List conform`() {
            val subject = listOf("catflap-0", "rubberplant-1", "marzipan-2")
            expectThat(subject).allIndexed { index ->
              endsWith("-$index")
            }
          }

          @Test
          fun `fails if any element of a List does not conform`() {
            val subject = listOf("catflap-1", "rubberplant-1", "marzipan-1")
            assertThrows<AssertionError> {
              expectThat(subject).allIndexed { index ->
                endsWith("-$index")
              }
            }
          }
        }

      @Nested
      inner class AnyAssertion {
        @TestFactory
        fun `passes if`() {
          listOf("catflap", "rubberplant", "marzipan")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("all elements of a ${subject.javaClass.simpleName} conform") {
                expectThat(subject).any {
                  isLowerCase()
                }
              }
            }

          listOf("catflap", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("one element of a ${subject.javaClass.simpleName} conforms") {
                expectThat(subject).any {
                  isLowerCase()
                }
              }
            }
        }

        @TestFactory
        fun `fails if no elements conform`() {
          listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("no elements of a ${subject.javaClass.simpleName} conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).any {
                    isLowerCase()
                  }
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

      @Nested
      inner class AnyIndexedAssertion {
          @Test
          fun `passes if all elements of a List conform`() {
            val subject = listOf("catflap-0", "rubberplant-1", "marzipan-2")
            expectThat(subject).anyIndexed { index ->
              endsWith("-$index")
            }
          }

          @Test
          fun `passes if one element of a List conforms`() {
            val subject = listOf("catflap-1", "rubberplant-1", "marzipan-1")
            expectThat(subject).anyIndexed { index ->
              endsWith("-$index")
            }
          }

          @Test
          fun `fails if no elements of a List conform`() {
            val subject = listOf("catflap", "rubberplant", "marzipan")
            assertThrows<AssertionError> {
              expectThat(subject).anyIndexed { index ->
                endsWith("-$index")
              }
            }
          }

        @Test
        fun `fails if works with not`() {
          val subject = setOf("catflap", "rubberplant", "marzipan")
          expectThat(subject).not().anyIndexed { index ->
            endsWith("-$index")
          }
        }
      }

      @Nested
      inner class NoneAssertion {
        @TestFactory
        fun `passes if`() {
          listOf("catflap", "rubberplant", "marzipan")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("no elements of a ${subject.javaClass.simpleName} conform") {
                expectThat(subject).none {
                  isUpperCase()
                }
              }
            }
        }

        @TestFactory
        fun `fails if`() {
          listOf("catflap", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("some elements of a ${subject.javaClass.simpleName} conforms") {
                assertThrows<AssertionError> {
                  expectThat(subject).none {
                    isUpperCase()
                  }
                }
              }
            }

          listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("all elements of a ${subject.javaClass.simpleName} conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).none {
                    isUpperCase()
                  }
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

        @Nested
        inner class NoneIndexedAssertion {
          @Test
          fun `passes if no elements of a List conform`() {
            val subject = listOf("catflap", "rubberplant", "marzipan")
            expectThat(subject).noneIndexed { index ->
              endsWith("-$index")
            }
          }

          @Test
          fun `fails if some elements of a List conform`() {
            val subject = listOf("catflap-1", "rubberplant-1", "marzipan-1")
            assertThrows<AssertionError> {
              expectThat(subject).noneIndexed { index ->
                endsWith("-$index")
              }
            }
          }

          @Test
          fun `fails if all elements of a List conform`() {
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

      @Nested
      inner class AtLeastAssertion {
        @TestFactory
        fun `at least assertion`() {
          listOf("catflap", "rubberplant", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("not enough elements of a ${subject.javaClass.simpleName} conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).atLeast(2) {
                    isUpperCase()
                  }
                }
              }
            }
        }

        @TestFactory
        fun `passes if`() {
          listOf("catflap", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("exactly minimum amount of elements of a ${subject.javaClass.simpleName} conforms") {
                expectThat(subject).atLeast(2) {
                  isUpperCase()
                }
              }
            }

          listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("all elements of a ${subject.javaClass.simpleName} conform") {
                expectThat(subject).atLeast(2) {
                  isUpperCase()
                }
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

      @Nested
      inner class AtMostAssertion {
        @TestFactory
        fun `passes if`() {
          listOf("catflap", "rubberplant", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("fewer elements of a ${subject.javaClass.simpleName} conform") {
                expectThat(subject).atMost(2) {
                  isUpperCase()
                }
              }
            }

          listOf("catflap", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("exactly maximum amount of elements of a ${subject.javaClass.simpleName} conforms") {
                expectThat(subject).atMost(2) {
                  isUpperCase()
                }
              }
            }
        }

        @TestFactory
        fun `fails if`() {
          listOf("CATFLAP", "RUBBERPLANT", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("too many elements of a ${subject.javaClass.simpleName} conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).atMost(2) {
                    isUpperCase()
                  }
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

      @Nested
      inner class OneAssertion {
        @TestFactory
        fun `passes if exactly one element conforms`() {
          listOf("catflap", "rubberplant", "MARZIPAN")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("exactly one element of a ${subject.javaClass.simpleName} conforms") {
                expectThat(subject).one {
                  isUpperCase()
                }
              }
            }
        }

        @TestFactory
        fun `fails if too many elements conform`() {
          listOf("CATFLAP", "RUBBERPLANT", "marzipan")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("too many elements of a ${subject.javaClass.simpleName} conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).one {
                    isUpperCase()
                  }
                }
              }
            }

          listOf("catflap", "rubberplant", "marzipan")
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("no elements of a ${subject.javaClass.simpleName} conform") {
                assertThrows<AssertionError> {
                  expectThat(subject).one {
                    isUpperCase()
                  }
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

      @Nested
      inner class ContainsAssertion {
        @TestFactory
        fun `passes if`() {
          listOf(
            listOf("catflap") to arrayOf("catflap"),
            listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap"),
            listOf("catflap", "rubberplant", "marzipan") to
              arrayOf(
                "catflap",
                "marzipan"
              )
          ).permuteExpected()
            .forEach { (subject, expected) ->
              DynamicTest.dynamicTest("$subject (${subject.javaClass.simpleName}) contains ${expected.toList()}") {
                expectThat(subject).contains(*expected)
              }
            }
        }

        @TestFactory
        fun `fails if`() {
          listOf(
            listOf("catflap", "rubberplant", "marzipan") to arrayOf("fnord"),
            listOf("catflap", "rubberplant", "marzipan") to
              arrayOf(
                "catflap",
                "fnord"
              ),
            emptyList<String>() to arrayOf("catflap")
          ).permuteExpected()
            .forEach { (subject, expected) ->
              DynamicTest.dynamicTest("$subject (${subject.javaClass.simpleName}) does not contain ${expected.toList()}") {
                assertThrows<AssertionError> {
                  expectThat(subject).contains(*expected)
                }
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

      @Nested
      inner class DoesNotContainAssertion {
        @TestFactory
        fun `passes if`() {
          emptyList<String>()
            .permute()
            .forEach { subject ->
              DynamicTest.dynamicTest("subject is empty ${subject.javaClass.simpleName}") {
                expectThat(subject)
                  .doesNotContain("catflap", "rubberplant", "marzipan")
              }
            }

          listOf(
            listOf("catflap", "rubberplant", "marzipan") to arrayOf("fnord"),
            listOf("catflap", "rubberplant", "marzipan") to
              arrayOf(
                "xenocracy",
                "wye",
                "exercitation"
              )
          ).permuteExpected()
            .forEach { (subject, elements) ->
              DynamicTest.dynamicTest("a ${subject.javaClass.simpleName} contains none of the elements ${elements.toList()}") {
                expectThat(subject)
                  .doesNotContain(*elements)
              }
            }
        }

        @TestFactory
        fun `fails if`() {
          listOf(
            emptyList(),
            listOf("catflap", "rubberplant", "marzipan")
          ).flatMap { it.permute() }
            .forEach { subject ->
              DynamicTest.dynamicTest("no elements are specified for subject $subject") {
                assertThrows<IllegalArgumentException> {
                  expectThat(subject).doesNotContain()
                }
              }
            }

          listOf(
            listOf("catflap", "rubberplant", "marzipan") to arrayOf("catflap"),
            listOf("catflap", "rubberplant", "marzipan") to
              arrayOf(
                "catflap",
                "kakistocracy",
                "impeach"
              ),
            listOf("catflap", "rubberplant", "marzipan") to
              arrayOf(
                "owlbear",
                "marzipan",
                "illithid"
              )
          )
            .permuteExpected()
            .forEach { (subject, elements) ->
              DynamicTest.dynamicTest("a ${subject.javaClass.simpleName} contains any of the elements ${elements.toList()}") {
                assertThrows<AssertionError> {
                  expectThat(subject)
                    .doesNotContain(*elements)
                }
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

      @Nested
      inner class ContainsExactlyAssertion {
        @Nested
        inner class ForSet {
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

        @Nested
        inner class ForList {
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

        @Nested
        inner class ANonCollectionIterableSubject {
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
      }

      @Nested
      inner class ContainsExactlyInAnyOrderAssertion {
        @Nested
        inner class ASet {
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

        @Nested
        inner class AList {
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

        @Nested
        inner class ANonCollectionIterableSubject {
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
      }

    @Nested
    inner class WithFirstFunction {
      @TestFactory
      fun `withFirst function`() {
        listOf("catflap", "rubberplant", "marzipan")
          .permute()
          .forEach { subject ->
            DynamicTest.dynamicTest("runs assertions on the first element of a ${subject.javaClass.simpleName}") {
              expectThat(subject).withFirst {
                isEqualTo("catflap")
              }
            }
            DynamicTest.dynamicTest("fails if the nested assertions fail on the first element of a ${subject.javaClass.simpleName}") {
              assertThrows<MultipleFailuresError> {
                expectThat(subject).withFirst {
                  isEqualTo("rubberplant")
                }
              }
            }
          }
      }
    }

    @Nested
    inner class WithFirstPredicateFunction {
      @TestFactory
      fun `withFirst(predicate) function`() {
        listOf("catflap", "rubberplant", "marzipan")
          .permute()
          .forEach { subject ->
            DynamicTest.dynamicTest("runs assertions on the first element of a ${subject.javaClass.simpleName} that matches the predicate") {
              expectThat(subject).withFirst({ it.startsWith('r') }) {
                isEqualTo("rubberplant")
              }
            }

            DynamicTest.dynamicTest(
              "fails if the nested assertions fail on the first element of a ${subject.javaClass.simpleName} that matches the predicate"
            ) {
              assertThrows<MultipleFailuresError> {
                expectThat(subject).withFirst({ it.startsWith('r') }) {
                  isEqualTo("catflap")
                }
              }
            }

            DynamicTest.dynamicTest("fails if nothing in a ${subject.javaClass.simpleName} matches the predicate") {
              assertThrows<MappingFailed> {
                expectThat(subject).withFirst({ it.startsWith('z') }) {
                  isEqualTo("catflap")
                }
              }
            }
          }
      }
    }

    @Nested
    inner class WithLastFunction {
      @TestFactory
      fun `withLast function`() {
        listOf("catflap", "rubberplant", "marzipan")
          .permute()
          .forEach { subject ->
            DynamicTest.dynamicTest("runs assertions on the last element of a ${subject.javaClass.simpleName}") {
              expectThat(subject.sorted()).withLast {
                isEqualTo("rubberplant")
              }
            }

            DynamicTest.dynamicTest("fails if the nested assertions fail on the last element of a ${subject.javaClass.simpleName}") {
              assertThrows<MultipleFailuresError> {
                expectThat(subject.sorted()).withLast {
                  isEqualTo("marzipan")
                }
              }
            }
          }
      }
    }

    @Nested
    inner class WithElementAtFunction {
      @TestFactory
      fun `withElementAt function`() {
        listOf("catflap", "rubberplant", "marzipan")
          .permute()
          .forEach { subject ->
            DynamicTest.dynamicTest("runs assertions on the nth element of a ${subject.javaClass.simpleName}") {
              expectThat(subject.sorted()).withElementAt(1) {
                isEqualTo("marzipan")
              }
            }

            DynamicTest.dynamicTest("fails if the nested assertions fail on the nth element of a ${subject.javaClass.simpleName}") {
              assertThrows<MultipleFailuresError> {
                expectThat(subject.sorted()).withElementAt(1) {
                  isEqualTo("catflap")
                }
              }
            }
          }
      }
    }

      @Nested
      inner class WithSingleFunction {
        @Test
        fun `runs assertions on the single element of a collection`() {
          expectThat(listOf("catflap")).withSingle {
            isEqualTo("catflap")
          }
        }

        @Test
        fun `fails if the nested assertions fail on the single element`() {
          assertThrows<MultipleFailuresError> {
            expectThat(listOf("catflap")).withSingle {
              isEqualTo("rubberplant")
            }
          }
        }

        @Test
        fun `fails if a collection is empty`() {
          assertThrows<MappingFailed> {
            expectThat(emptyList<String>()).withSingle {
              isEqualTo("rubberplant")
            }
          }
        }

        @Test
        fun `fails if a collection contains more than one element`() {
          assertThrows<MappingFailed> {
            expectThat(listOf("catflap", "rubberplant", "marzipan")).withSingle {
              isEqualTo("rubberplant")
            }
          }
        }
      }

      @Nested
      inner class CountMapping {
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

      @Nested
      inner class IsSortedWithComparatorAssertion {
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
        fun `the assertion passes if the subject is in order`() {
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
      fun `the assertion passes if the Null value is handled through the Comparator instance`() {
        expectThat(listOf("catflap", "marzipan", null))
          .isSorted(Comparator.nullsLast(Comparator.naturalOrder<String>()))
      }

      @Test
      fun `fails with NPE if the Null value isn't handled through the Comparator instances`() {
        assertThrows<NullPointerException> {
          expectThat(listOf("catflap", "marzipan", null))
            .isSorted(Comparator.naturalOrder<String>())
        }
      }

      @Nested
      inner class IsSortedOnNonComparable {
          private val subject =
            expectThat(
              listOf(
                listOf("catflap"),
                listOf("marzipan", "persipan"),
                listOf("rubberplant", "rubber bush", "rubber tree")
              )
            )

        @Test
        fun `the assertion passes`() {
          subject.run {
            isSorted(Comparator.comparing { it?.size ?: 0 })
          }
        }

        @Test
        fun `fails if the subject is not sorted according to the comparator`() {
          subject.run {
            assertThrows<AssertionError> {
              isSorted(Comparator.comparing(Collection<String>::size).reversed())
            }
          }
        }
      }
    }

      @Nested
      inner class IsSortedAssertion {
        @Test
        fun `passes if the subject is empty`() {
          expectThat(emptyList<String>()).isSorted()
        }

        @Test
        fun `the assertion passes if the elements are in natural order`() {
          expectThat(listOf("catflap", "marzipan", "rubberplant"))
            .isSorted()
        }

        @Test
        fun `the assertion fails if the elements are not in natural order`() {
          assertThrows<AssertionError> {
            expectThat(listOf("catflap", "rubberplant", "marzipan"))
              .isSorted()
          }
        }
      }
    }
