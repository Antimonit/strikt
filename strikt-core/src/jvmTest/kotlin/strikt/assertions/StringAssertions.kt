package strikt.assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.internal.opentest4j.CompoundAssertionFailure

internal class StringAssertionsIsEqualToIgnoringCase {

  private val subject = expectThat("fnord")

  @Test
  fun `passes if the subject is identical to the expected value`() {
    subject.isEqualToIgnoringCase("fnord")
  }

  @Test
  fun `fails if the subject is different`() {
    assertThrows<AssertionError> {
      expectThat("despite the negative press fnord")
        .isEqualToIgnoringCase("fnord")
    }
  }

  @Test
  fun `passes if the subject is the same as the expected value apart from case`() {
    expectThat("fnord").isEqualToIgnoringCase("fnord")
  }
}

internal class StringAssertionsStartsWith {

  @Test
  fun `can expect string start`() {
    expectThat("fnord").startsWith("fno")
  }

  @Test
  fun `outputs real start when startsWith fails`() {
    val expectThrows =
      expectThrows<AssertionError> {
        try {
          expectThat("fnord").startsWith("fnrd")
        } catch (e: Throwable) {
          throw e
        }
      }
    expectThrows
      .message
      .isNotNull()
      .contains(
        """▼ Expect that "fnord":
              |  ✗ starts with "fnrd"
              |          found "fnor"
        """.trimMargin()
      )
  }
}

internal class StringAssertionsEndsWith {

  @Test
  fun `can expect string end`() {
    expectThat("fnord").endsWith("nord")
  }

  @Test
  fun `outputs real end when endsWith fails`() {
    expectThrows<AssertionError> {
      expectThat("fnord").endsWith("nor")
    }
      .message
      .isNotNull()
      .isEqualTo(
        """▼ Expect that "fnord":
               |  ✗ ends with "nor"
               |        found "ord"
        """.trimMargin()
      )
  }
}

@DisplayName("assertions on String")
internal class StringAssertions {

  @Test
  fun `can have a block assertion on a string subject without overload clash`() {
    val error =
      assertThrows<CompoundAssertionFailure> {
        val subject = "The Enlightened take things Lightly"
        expectThat(subject = subject) {
          hasLength(5)
          matches(Regex("\\d+"))
          startsWith("T")
        }
      }
    expectThat(error.failures.size).isEqualTo(2)
  }

  @Test
  fun `can trim string`() {
    expectThat(" fnord ").trim().isEqualToIgnoringCase("fnord")
  }
}
