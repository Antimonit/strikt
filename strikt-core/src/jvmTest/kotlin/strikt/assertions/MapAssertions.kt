package strikt.assertions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import org.opentest4j.MultipleFailuresError
import strikt.api.expectThat
import strikt.internal.opentest4j.MappingFailed

internal class MapAssertions {
  @Nested
  inner class AnEmptySubject {
    private val subject = expectThat(emptyMap<String, String>())

    @Test
    fun `isEmpty assertion passes`() {
      subject.run {
        isEmpty()
      }
    }

    @Test
    fun `isNotEmpty assertion fails`() {
      subject.run {
          assertThrows<AssertionError> {
            isNotEmpty()
          }
      }
    }

    @Test
    fun `get mapping returns a null subject`() {
      subject.run {
          get("foo").isNull()
      }
    }

    @Test
    fun `withValue throws an exception`() {
      subject.run {
          assertThrows<MappingFailed> {
            withValue("foo") {
              isNotBlank()
            }
          }
      }
    }

    @Test
    fun `keys mapping returns an empty set subject`() {
      subject.run {
          keys.isEmpty()
      }
    }

    @Test
    fun `values mapping returns an empty collection subject`() {
      subject.run {
          values.isEmpty()
      }
    }
  }

  @Nested
  inner class ANonEmptyMap {
    private val subject =
          expectThat(
            mapOf(
              "foo" to "bar",
              "baz" to "fnord",
              "qux" to "fnord"
            )
          )

    @Test
    fun `isEmpty assertion fails`() {
      subject.run {
          assertThrows<AssertionError> {
            isEmpty()
          }
      }
    }

    @Test
    fun `isNotEmpty assertion passes`() {
      subject.run {
          isNotEmpty()
      }
    }

    @Nested
    inner class ContainsKeyAssertion {

      @Test
      fun `passes if the subject has a matching key`() {
        subject.run {
            containsKey("foo")
        }
      }

      @Test
      fun `fails if the subject does not have a matching key`() {
        subject.run {
            val error =
              assertThrows<AssertionError> {
                containsKey("bar")
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
              |  ✗ has an entry with the key "bar""""
                .trimMargin()
            )
        }
      }
    }

    @Nested
    inner class DoesNotContainKeyAssertion {

      @Test
      fun `passes if the subject doesn't have a matching key`() {
        subject.run {
            doesNotContainKey("bar")
        }
      }

      @Test
      fun `fails if the subject does have a matching key`() {
        subject.run {
            val error =
              assertThrows<AssertionError> {
                doesNotContainKey("foo")
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
              |  ✗ does not have an entry with the key "foo""""
                .trimMargin()
            )
        }
      }
    }

    @Nested
    inner class ContainsKeysAssertion {

      @Test
      fun `passes if the subject has all the specified keys`() {
        subject.run {
            containsKeys("foo", "baz")
        }
      }

      @Test
      fun `fails if the subject does not have a matching key`() {
        subject.run {
            val error =
              assertThrows<AssertionError> {
                containsKeys("foo", "bar", "fnord")
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
              |  ✗ has entries with the keys ["foo", "bar", "fnord"]
              |    ✓ has an entry with the key "foo"
              |    ✗ has an entry with the key "bar"
              |    ✗ has an entry with the key "fnord""""
                .trimMargin()
            )
        }
      }
    }

    @Nested
    inner class DoesNotContainKeysAssertion {

      @Test
      fun `passes if the subject does not have all the specified keys`() {
        subject.run {
            doesNotContainKeys("bar", "fnord")
        }
      }

      @Test
      fun `fails if the subject does have a matching key`() {
        subject.run {
            val error =
              assertThrows<AssertionError> {
                doesNotContainKeys("bar", "fnord", "foo")
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
              |  ✗ doesn't have entries with the keys ["bar", "fnord", "foo"]
              |    ✓ does not have an entry with the key "bar"
              |    ✓ does not have an entry with the key "fnord"
              |    ✗ does not have an entry with the key "foo""""
                .trimMargin()
            )
        }
      }
    }

    @Nested
    inner class HasEntryAssertion {

      @Test
      fun `passes if the subject has a matching key value pair`() {
        subject.run {
            hasEntry("foo", "bar")
        }
      }

      @Test
      fun `fails if the subject does not have a matching key`() {
        subject.run {
            val error =
              assertThrows<AssertionError> {
                hasEntry("bar", "foo")
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
              |  ✗ has an entry with the key "bar""""
                .trimMargin()
            )
        }
      }

      @Test
      fun `fails if the subject has a different value associated with the key`() {
        subject.run {
            val error =
              assertThrows<AssertionError> {
                hasEntry("foo", "baz")
              }
            expectThat(error.message).isEqualTo(
              """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
              |  ✓ has an entry with the key "foo"
              |  ▼ entry ["foo"]:
              |    ✗ is equal to "baz"
              |            found "bar""""
                .trimMargin()
            )
        }
      }
    }

    @Nested
    inner class GetMapping {

      @Test
      fun `returns an assertion over the value for a valid key`() {
        subject.run {
            get("foo").isEqualTo("bar")
        }
      }

      @Test
      fun `returns a null subject for a non-existent key`() {
        subject.run {
            get("bar").isNull()
        }
      }
    }

    @Nested
    inner class GetValueMapping {

      @Test
      fun `returns an assertion over the value for a valid key`() {
        subject.run {
            getValue("foo").isEqualTo("bar")
        }
      }

      @Test
      fun `fails for a non-existent key`() {
        subject.run {
            assertThrows<AssertionFailedError> {
              getValue("bar").isEqualTo("this will never get evaluated")
            }.also {
              expectThat(it.message)
                .isEqualTo(
                  """
                |▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
                |  ✗ has an entry with the key "bar"
                  """.trimMargin()
                )
            }
        }
      }
    }

    @Nested
    inner class WithValueFunction {

      @Test
      fun `runs assertions on the value associated with the key`() {
        subject.run {
            withValue("foo") {
              isEqualTo("bar")
            }
        }
      }

      @Test
      fun `fails if the nested assertions fail`() {
        subject.run {
            assertThrows<MultipleFailuresError> {
              withValue("foo") {
                isEqualTo("baz")
              }
            }
        }
      }

      @Test
      fun `fails for a non-existent key`() {
        subject.run {
            assertThrows<MappingFailed> {
              withValue("bar") {
                isEqualTo("this will never get evaluated")
              }
            }.also {
              expectThat(it.message)
                .isEqualTo("Mapping 'entry [\"bar\"]' failed with: Key bar is missing in the map.")
            }
          }
      }
    }

    @Nested
    inner class KeysMapping {

      @Test
      fun `returns the map keys as a subject`() {
        subject.run {
            keys.isEqualTo(setOf("foo", "baz", "qux"))
        }
      }
    }

    @Nested
    inner class ValuesMapping {

      @Test
      fun `returns the map values as a subject`() {
        subject.run {
            values.containsExactlyInAnyOrder("bar", "fnord", "fnord")
        }
      }
    }
  }
}
