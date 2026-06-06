package strikt.assertions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import org.opentest4j.MultipleFailuresError
import strikt.api.expectThat
import strikt.internal.opentest4j.MappingFailed

class EmptyMapAssertions {
  private val subject = expectThat(emptyMap<String, String>())

  @Test
  fun `isEmpty assertion passes`() {
    subject.isEmpty()
  }

  @Test
  fun `isNotEmpty assertion fails`() {
    assertThrows<AssertionError> {
      subject.isNotEmpty()
    }
  }

  @Test
  fun `get mapping returns a null subject`() {
    subject.get("foo").isNull()
  }

  @Test
  fun `withValue throws an exception`() {
    assertThrows<MappingFailed> {
      subject.withValue("foo") {
        isNotBlank()
      }
    }
  }

  @Test
  fun `keys mapping returns an empty set subject`() {
    subject.keys.isEmpty()
  }

  @Test
  fun `values mapping returns an empty collection subject`() {
    subject.values.isEmpty()
  }
}

class NonEmptyMapAssertions {

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
    assertThrows<AssertionError> {
      subject.isEmpty()
    }
  }

  @Test
  fun `isNotEmpty assertion passes`() {
    subject.isNotEmpty()
  }

  @Test
  fun `containsKey passes if the subject has a matching key`() {
    subject.containsKey("foo")
  }

  @Test
  fun `containsKey fails if the subject does not have a matching key`() {
    val error =
      assertThrows<AssertionError> {
        subject.containsKey("bar")
      }
    assertEquals(
      """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
        |  ✗ has an entry with the key "bar""""
        .trimMargin(),
      error.message,
    )
  }

  @Test
  fun `doesNotContainKey passes if the subject doesn't have a matching key`() {
    subject.doesNotContainKey("bar")
  }

  @Test
  fun `doesNotContainKey fails if the subject does have a matching key`() {
    val error =
      assertThrows<AssertionError> {
        subject.doesNotContainKey("foo")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
      |  ✗ does not have an entry with the key "foo""""
        .trimMargin()
    )
  }

  @Test
  fun `containsKeys passes if the subject has all the specified keys`() {
    subject.containsKeys("foo", "baz")
  }

  @Test
  fun `containsKeys fails if the subject does not have a matching key`() {
    val error =
      assertThrows<AssertionError> {
        subject.containsKeys("foo", "bar", "fnord")
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

  @Test
  fun `doesNotContainKeys passes if the subject does not have all the specified keys`() {
    subject.doesNotContainKeys("bar", "fnord")
  }

  @Test
  fun `doesNotContainKeys fails if the subject does have a matching key`() {
    val error =
      assertThrows<AssertionError> {
        subject.doesNotContainKeys("bar", "fnord", "foo")
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

  @Test
  fun `hasEntry passes if the subject has a matching key value pair`() {
    subject.hasEntry("foo", "bar")
  }

  @Test
  fun `hasEntry fails if the subject does not have a matching key`() {
    val error =
      assertThrows<AssertionError> {
        subject.hasEntry("bar", "foo")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that {"foo"="bar", "baz"="fnord", "qux"="fnord"}:
      |  ✗ has an entry with the key "bar""""
        .trimMargin()
    )
  }

  @Test
  fun `hasEntry fails if the subject has a different value associated with the key`() {
    val error =
      assertThrows<AssertionError> {
        subject.hasEntry("foo", "baz")
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

  @Test
  fun `get returns an assertion over the value for a valid key`() {
    subject.get("foo").isEqualTo("bar")
  }

  @Test
  fun `get returns a null subject for a non-existent key`() {
    subject.get("bar").isNull()
  }

  @Test
  fun `getValue returns an assertion over the value for a valid key`() {
    subject.getValue("foo").isEqualTo("bar")
  }

  @Test
  fun `getValue fails for a non-existent key`() {
    assertThrows<AssertionFailedError> {
      subject.getValue("bar").isEqualTo("this will never get evaluated")
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

  @Test
  fun `withValue runs assertions on the value associated with the key`() {
    subject.withValue("foo") {
      isEqualTo("bar")
    }
  }

  @Test
  fun `withValue fails if the nested assertions fail`() {
    assertThrows<MultipleFailuresError> {
      subject.withValue("foo") {
        isEqualTo("baz")
      }
    }
  }

  @Test
  fun `withValue fails for a non-existent key`() {
    assertThrows<MappingFailed> {
      subject.withValue("bar") {
        isEqualTo("this will never get evaluated")
      }
    }.also {
      expectThat(it.message)
        .isEqualTo("Mapping 'entry [\"bar\"]' failed with: Key bar is missing in the map.")
    }
  }

  @Test
  fun `keys mapping returns the map keys as a subject`() {
    subject.keys.isEqualTo(setOf("foo", "baz", "qux"))
  }

  @Test
  fun `values mapping returns the map values as a subject`() {
    subject.values.containsExactlyInAnyOrder("bar", "fnord", "fnord")
  }
}
