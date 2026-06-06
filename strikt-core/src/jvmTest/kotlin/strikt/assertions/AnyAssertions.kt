package strikt.assertions

import jdk.dynalink.linker.support.Guards.isNull
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.Assertion.Builder
import strikt.api.expectThat
import kotlin.time.Instant

internal class AnyAssertions {
    @Nested
    inner class IsNull {
      @Nested
      inner class WhenSubjectIsNull {
        private val fixture: Builder<Any?> = expectThat(null)

        @Test
        fun `the assertion passes`() {
          fixture.run {
            isNull()
          }
        }

        @Test
        fun `the assertion down-casts the subject`() {
          fixture.run {
            @Suppress("USELESS_IS_CHECK")
            also { assert(it is Builder<Any?>) }
              .isNull()
              .also { assert(it is Builder<Nothing>) }
          }
        }
      }

      @TestFactory
      fun `the assertion fails for non-null subjects`() {
        listOf("fnord", 1L, "null").forEach {
          val fixture = expectThat(it)

          fixture.run {
            DynamicTest.dynamicTest("a non-null subject : ${it.quoted()}") {
              assertThrows<AssertionFailedError> {
                isNull()
              }
            }
          }
        }
      }
    }

    @Nested
    inner class IsNotNull {
      @Nested
      inner class WhenSubjectIsNull {
        val fixture = expectThat(null)

        @Test
        fun `the assertion fails`() {
          fixture.run {
            assertThrows<AssertionFailedError> {
              isNotNull()
            }
          }
        }
      }

      @TestFactory
      fun `the assertion passes for non-null subjects`() {
        listOf("fnord", 1L, "null").forEach<Any?> {
          val fixture = expectThat(it)

          fixture.run {
            DynamicTest.dynamicTest("a non-null subject : ${it.quoted()}") {
              isNotNull()
            }

            DynamicTest.dynamicTest("a non-null subject : ${it.quoted()}") {
              @Suppress("USELESS_IS_CHECK")
              also { assert(it is Builder<Any?>) }
                .isNotNull()
                .also { assert(it is Builder<Any>) }
            }
          }
        }
      }
    }

    @Nested
    inner class WithNotNull {
      @Nested
      inner class WhenSubjectIsNull {
        val subject = expectThat(null)

        @Test
        fun `the assertion fails`() {
          subject.run {
            assertThrows<AssertionFailedError> {
              withNotNull {
                isEqualTo("fnord")
              }
            }
          }
        }
      }

      @TestFactory
      fun `the assertion passes for non-null subjects`() {
        listOf("fnord", 1L, "null").forEach<Any?> {
          val fixture = expectThat(it)

          DynamicTest.dynamicTest("a non-null subject : ${it.quoted()}") {
            fixture.run {
              withNotNull {
                isEqualTo(it)
              }
            }
          }
        }
      }
    }

  @Nested
  inner class IsA {
      @Nested
      inner class WhenSubjectIsNull {
        val fixture = expectThat(null)
        @Test
        fun `the assertion fails`() {
          fixture.run {
            assertThrows<AssertionFailedError> {
              isA<String>()
            }
          }
        }
      }

      @Nested
      inner class WhenSubjectIsOfWrongType {
        val fixture = expectThat(1L)

        @Test
        fun `the assertion fails`() {
          fixture.run {
            assertThrows<AssertionFailedError> {
              isA<String>()
            }
          }
        }
      }

      @Nested
      inner class WhenSubjectIsOfExpectedType {
        private val fixture: Builder<Any?> = expectThat("fnord")

        @Test
        fun `the assertion passes`() {
          fixture.run {
            isA<String>()
          }
        }

        @Test
        fun `the assertion narrows the subject type`() {
          fixture.run {
            @Suppress("USELESS_IS_CHECK")
            also { assert(it is Builder<Any?>) }
              .isA<String>()
              .also { assert(it is Builder<String>) }
          }
        }

        @Test
        fun `the narrowed type can use specialized assertions`() {
          fixture.run {
            isA<String>().hasLength(5) // only available on Assertion<CharSequence>
          }
        }
      }

      @Nested
      inner class WhenSubjectIsASubtype {
        private val fixture: Builder<Any?> = expectThat(1L)

        @Test
        fun `the assertion passes`() {
          fixture.run {
            isA<Number>()
          }
        }

        @Test
        fun `the assertion narrows the subject type`() {
          fixture.run {
            @Suppress("USELESS_IS_CHECK")
            also { assert(it is Builder<Any?>) }
              .isA<Number>()
              .also { assert(it is Builder<Number>) }
              .isA<Long>()
              .also { assert(it is Builder<Long>) }
          }
        }
      }
    }

    @Nested
    inner class IsEqualTo {
      @Nested
      inner class WhenSubjectIsEqualToExpectation {
        val fixture = expectThat("fnord")

        @Test
        fun `the assertion passes`() {
          fixture.run {
            isEqualTo("fnord")
          }
        }
      }

      @TestFactory
      fun `the assertion fails for non-equal pairs`() {
        listOf(
          "fnord" to "FNORD",
          1 to 1L,
          null to "fnord",
          "fnord" to null
        )
          .forEach { (subject, expected) ->
            DynamicTest.dynamicTest("when the subject is ${subject.quoted()} the assertion fails") {
              val fixture = expectThat(subject)

              fixture.run {
                assertThrows<AssertionFailedError> {
                  isEqualTo(expected)
                }
              }
            }
          }
      }

      @Nested
      inner class WhenSubjectIsDifferentTypeThatLooksTheSame {
        val fixture = expectThat(5L)

        @Test
        fun `the failure message specifies the types involved`() {
          fixture.run {
            val error =
              assertThrows<AssertionFailedError> {
                isEqualTo(5)
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that 5:
            |  ✗ is equal to 5 (Int)
            |          found 5 (Long)
              """.trimMargin()
            )
          }
        }
      }
    }

    @Nested
    inner class IsNotEqualTo {
      @Nested
      inner class WhenSubjectMatchesExpectation {
        val fixture = expectThat("fnord")

        @Test
        fun `the assertion fails`() {
          fixture.run {
            assertThrows<AssertionFailedError> {
              isNotEqualTo("fnord")
            }
          }
        }
      }

      @TestFactory
      fun `the assertion passes for non-equal pairs`() {
        listOf(
          "fnord" to "FNORD",
          1 to 1L,
          null to "fnord",
          "fnord" to null
        )
          .forEach { (subject, expected) ->
            val fixture = expectThat(subject)

            fixture.run {
              DynamicTest.dynamicTest("when the subject is ${subject.quoted()} and expected is ${expected.quoted()}") {
                isNotEqualTo(expected)
              }
            }
          }
      }
    }

    @Nested
    inner class IsSameInstanceAs {
      @TestFactory
      fun `the assertion fails for different instances`() {
        listOf(
          listOf("fnord") to listOf("fnord"),
          null to listOf("fnord"),
          listOf("fnord") to null,
          1 to 1L,
          Instant.parse("2026-12-31T12:30:00Z").let { it to Instant.fromEpochMilliseconds(it.toEpochMilliseconds()) }
        )
          .forEach { (subject, expected) ->
            val fixture = expectThat(subject)

            fixture.run {
              DynamicTest.dynamicTest("the subject and expected values are different instances") {
                assertThrows<AssertionFailedError> {
                  isSameInstanceAs(expected)
                }
              }
            }
          }
      }

      @TestFactory
      fun `the assertion passes for same instances`() {
        listOf("fnord", 1L, null, listOf("fnord"), Instant.parse("2026-12-31T12:30:00Z"))
          .map { it to it }
          .forEach { (subject, expected) ->
            val fixture = expectThat(subject)

            fixture.run {
              DynamicTest.dynamicTest("the subject and expected values are the same instance: ${subject.quoted()}") {
                isSameInstanceAs(expected)
              }
            }
          }
      }
    }

    @Nested
    inner class IsNotSameInstanceAs {
      @TestFactory
      fun `the assertion passes for different instances`() {
        listOf(
          listOf("fnord") to listOf("fnord"),
          null to listOf("fnord"),
          listOf("fnord") to null,
          1 to 1L,
          Instant.parse("2026-12-31T12:30:00Z").let { it to Instant.fromEpochMilliseconds(it.toEpochMilliseconds()) }
        )
          .forEach { (subject, expected) ->
            val fixture = expectThat(subject)

            fixture.run {
              DynamicTest.dynamicTest("the subject and expected values are different instances") {
                isNotSameInstanceAs(expected)
              }
            }
          }
      }

      @TestFactory
      fun `the assertion fails for same instances`() {
        listOf("fnord", 1L, null, listOf("fnord"), Instant.fromEpochMilliseconds(0))
          .map { it to it }
          .forEach { (subject, expected) ->
            val fixture = expectThat(subject)

            fixture.run {
              DynamicTest.dynamicTest("the subject and expected values are the same instance: ${subject.quoted()}") {
                assertThrows<AssertionFailedError> {
                  isNotSameInstanceAs(expected)
                }
              }
            }
          }
      }
    }

    @Nested
    inner class IsContainedIn {
      @Nested
      inner class WithACollectionOfTheSameType {
        val fixture =
            expectThat("fnord")

        @Test
        fun `fails if the subject is not in the expected iterable`() {
          fixture.run {
            assertThrows<AssertionFailedError> {
              isContainedIn(listOf("catflap", "rubberplant", "marzipan"))
            }
          }
        }

        @Test
        fun `passes if the subject is in the expected iterable`() {
          fixture.run {
            isContainedIn(listOf("catflap", "rubberplant", "marzipan", "fnord"))
          }
        }
      }

      @Nested
      inner class WithACollectionOfASuperType {
        val fixture = expectThat(1)
        @Test
        fun `passes if the subject is in the expected iterable`() {
          fixture.run {
            // this is really just testing compilation works
            isContainedIn(listOf<Number>(1, 1L, 1.0, 1.0f))
          }
        }
      }
    }
}
