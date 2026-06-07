package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat

internal class CharSequenceHasLength {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject has the expected length`() {
    subject.run {
      hasLength(5)
    }
  }

  @Test
  fun `fails when the subject does not have the expected length`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        hasLength(1)
      }
    }
  }
}

internal class CharSequenceMatches {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.run {
      matches("[dfnor]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject is only a partial match for the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        matches("[fn]+".toRegex())
      }
    }
  }

  @Test
  fun `fails when the subject is a case insensitive match for the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        matches("[DFNOR]+".toRegex())
      }
    }
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        matches("\\d+".toRegex())
      }
    }
  }
}

internal class CharSequenceMatchesIgnoringCase {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.run {
      matchesIgnoringCase("[dfnor]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject is only a partial match for the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        matchesIgnoringCase("[fn]+".toRegex())
      }
    }
  }

  @Test
  fun `passes when the subject is a case insensitive match for the regex`() {
    subject.run {
      matchesIgnoringCase("[DFNOR]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        matchesIgnoringCase("\\d+".toRegex())
      }
    }
  }
}

internal class CharSequenceContainsRegex {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.run {
      contains("[dfnor]+".toRegex())
    }
  }

  @Test
  fun `passes when the subject is only a partial match for the regex`() {
    subject.run {
      contains("[fn]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject contains a match with a different case`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        contains("[DFNOR]+".toRegex())
      }
    }
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        contains("\\d+".toRegex())
      }
    }
  }
}

internal class CharSequenceContainsIgnoringCaseRegex {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.run {
      containsIgnoringCase("[dfnor]+".toRegex())
    }
  }

  @Test
  fun `passes when the subject is only a partial match for the regex`() {
    subject.run {
      containsIgnoringCase("[fn]+".toRegex())
    }
  }

  @Test
  fun `passes when the subject contains a match with a different case`() {
    subject.run {
      containsIgnoringCase("[FN]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        containsIgnoringCase("\\d+".toRegex())
      }
    }
  }
}

internal class CharSequenceContainsCharSequence {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject contains the expected substring`() {
    subject.run {
      contains("nor")
    }
  }

  @Test
  fun `fails when the subject contains the expected substring in a different case`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        contains("NOR")
      }
    }
  }

  @Test
  fun `fails when the subject does not contain the expected substring`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        contains("meme")
      }
    }
  }
}

internal class CharSequenceContainsIgnoringCaseCharSequence {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject contains the expected substring`() {
    subject.run {
      containsIgnoringCase("nor")
    }
  }

  @Test
  fun `passes when the subject contains the expected substring in a different case`() {
    subject.run {
      containsIgnoringCase("NOR")
    }
  }

  @Test
  fun `fails when the subject does not contain the expected substring`() {
    subject.run {
      assertThrows<AssertionFailedError> {
        containsIgnoringCase("meme")
      }
    }
  }
}

internal class CharSequenceIsNullOrEmpty {

  @Test
  fun `passes when the subject is null or empty`() {
    listOf("", null).forEach<CharSequence?> { value ->
      expectThat(value).isNullOrEmpty()
    }
  }

  @Test
  fun `fails when the subject is neither null nor empty`() {
    listOf("catflap", " ", "\t", "a", "23", "[]").forEach<CharSequence?> { subject ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isNullOrEmpty()
      }
    }
  }
}

internal class CharSequenceIsNullOrBlank {

  @Test
  fun `passes when the subject is null or blank`() {
    listOf("", null, "\t", "     ", " \n \r\n\t\n").forEach<CharSequence?> { subject ->
      expectThat(subject).isNullOrBlank()
    }
  }

  @Test
  fun `fails when the subject is neither null nor blank`() {
    listOf("catflap", "a", "73", "[]").forEach<CharSequence?> { subject ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isNullOrBlank()
      }
    }
  }
}

internal class CharSequenceIsEmpty {

  @Test
  fun `passes when the subject is empty`() {
    expectThat("").isEmpty()
  }

  @Test
  fun `fails when the subject is not empty`() {
    listOf("catflap", " ", "\t", "a", "73", "[]").forEach { subject ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isEmpty()
      }
    }
  }
}

internal class CharSequenceIsBlank {

  @Test
  fun `passes when the subject is blank`() {
    listOf(
      "",
      "\t",
      "     ",
      " \n \r\n\t\n"
    ).forEach<CharSequence> { subject ->
      expectThat(subject).isBlank()
    }
  }

  @Test
  fun `fails when the subject is not blank`() {
    listOf("catflap", "a", "23", "[]").forEach<CharSequence> { subject ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isBlank()
      }
    }
  }
}

internal class CharSequenceIsNotEmpty {

  @Test
  fun `fails when the subject is empty`() {
    assertThrows<AssertionFailedError> {
      expectThat("").isNotEmpty()
    }
  }

  @Test
  fun `passes when the subject is not empty`() {
    listOf("catflap", " ", "\t", "a", "73", "[]").forEach<CharSequence> { subject ->
      expectThat(subject).isNotEmpty()
    }
  }
}

internal class CharSequenceIsNotBlank {

  @Test
  fun `fails when the subject is blank`() {
    listOf(
      "",
      "\t",
      "     ",
      " \n \r\n\t\n"
    ).forEach<CharSequence> { subject ->
      assertThrows<AssertionFailedError> {
        expectThat(subject).isNotBlank()
      }
    }
  }

  @Test
  fun `passes when the subject is not blank`() {
    listOf("catflap", "a", "73", "[]").forEach { subject ->
      expectThat(subject).isNotBlank()
    }
  }
}

internal class CharSequenceTrim {

  @Test
  fun `can trim char sequence`() {
    expectThat(StringBuilder(" fnord "))
      .trim().isEqualTo("fnord")
  }
}
