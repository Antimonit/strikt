package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat

internal class CharSequenceHasLength {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject has the expected length`() {
    subject.hasLength(5)
  }

  @Test
  fun `fails when the subject does not have the expected length`() {
    assertThrows<AssertionFailedError> {
      subject.hasLength(1)
    }
  }
}

internal class CharSequenceMatches {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.matches("[dfnor]+".toRegex())
  }

  @Test
  fun `fails when the subject is only a partial match for the regex`() {
    assertThrows<AssertionFailedError> {
      subject.matches("[fn]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject is a case insensitive match for the regex`() {
    assertThrows<AssertionFailedError> {
      subject.matches("[DFNOR]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    assertThrows<AssertionFailedError> {
      subject.matches("\\d+".toRegex())
    }
  }
}

internal class CharSequenceMatchesIgnoringCase {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.matchesIgnoringCase("[dfnor]+".toRegex())
  }

  @Test
  fun `fails when the subject is only a partial match for the regex`() {
    assertThrows<AssertionFailedError> {
      subject.matchesIgnoringCase("[fn]+".toRegex())
    }
  }

  @Test
  fun `passes when the subject is a case insensitive match for the regex`() {
    subject.matchesIgnoringCase("[DFNOR]+".toRegex())
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    assertThrows<AssertionFailedError> {
      subject.matchesIgnoringCase("\\d+".toRegex())
    }
  }
}

internal class CharSequenceContainsRegex {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.contains("[dfnor]+".toRegex())
  }

  @Test
  fun `passes when the subject is only a partial match for the regex`() {
    subject.contains("[fn]+".toRegex())
  }

  @Test
  fun `fails when the subject contains a match with a different case`() {
    assertThrows<AssertionFailedError> {
      subject.contains("[DFNOR]+".toRegex())
    }
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    assertThrows<AssertionFailedError> {
      subject.contains("\\d+".toRegex())
    }
  }
}

internal class CharSequenceContainsIgnoringCaseRegex {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject is a full match for the regex`() {
    subject.containsIgnoringCase("[dfnor]+".toRegex())
  }

  @Test
  fun `passes when the subject is only a partial match for the regex`() {
    subject.containsIgnoringCase("[fn]+".toRegex())
  }

  @Test
  fun `passes when the subject contains a match with a different case`() {
    subject.containsIgnoringCase("[FN]+".toRegex())
  }

  @Test
  fun `fails when the subject does not match the regex`() {
    assertThrows<AssertionFailedError> {
      subject.containsIgnoringCase("\\d+".toRegex())
    }
  }
}

internal class CharSequenceContainsCharSequence {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject contains the expected substring`() {
    subject.contains("nor")
  }

  @Test
  fun `fails when the subject contains the expected substring in a different case`() {
    assertThrows<AssertionFailedError> {
      subject.contains("NOR")
    }
  }

  @Test
  fun `fails when the subject does not contain the expected substring`() {
    assertThrows<AssertionFailedError> {
      subject.contains("meme")
    }
  }
}

internal class CharSequenceContainsIgnoringCaseCharSequence {
  private val subject = expectThat("fnord")

  @Test
  fun `passes when the subject contains the expected substring`() {
    subject.containsIgnoringCase("nor")
  }

  @Test
  fun `passes when the subject contains the expected substring in a different case`() {
    subject.containsIgnoringCase("NOR")
  }

  @Test
  fun `fails when the subject does not contain the expected substring`() {
    assertThrows<AssertionFailedError> {
      subject.containsIgnoringCase("meme")
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
