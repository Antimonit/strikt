package strikt.assertions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

internal class CollectionAssertions {
    @Nested
    inner class HasSize {
      private val fixture = expectThat(setOf("catflap", "rubberplant", "marzipan"))

      @Test
      fun `passes if the subject is the expected size`() {
        fixture.run {
          hasSize(3)
        }
      }

      @Test
      fun `fails if the subject is not the expected size`() {
        fixture.run {
          assertThrows<AssertionError> {
            hasSize(1)
          }
        }
      }
    }

    @Nested
    inner class IsEmpty {
      @Nested
      inner class AnEmptyCollectionSubject {
        private val fixture = expectThat(emptyList<Any>())

        @Test
        fun `an empty collection subject passes`() {
          fixture.run {
            isEmpty()
          }
        }
      }

      @Nested
      inner class ANonEmptyCollectionSubject {
        private val fixture = expectThat(listOf("catflap", "rubberplant", "marzipan"))

        @Test
        fun `an non-empty collection subject fails`() {
          fixture.run {
            assertThrows<AssertionError> {
              isEmpty()
            }
          }
        }
      }
    }

    @Nested
    inner class IsNotEmpty {
      @Nested
      inner class AnEmptyCollectionSubject {
        private val fixture = expectThat(emptyList<Any>())

        @Test
        fun `an empty collection subject fails`() {
          fixture.run {
            assertThrows<AssertionError> {
              isNotEmpty()
            }
          }
        }
      }

      @Nested
      inner class ANonEmptyCollectionSubject {
        private val fixture = expectThat(listOf("catflap", "rubberplant", "marzipan"))

        @Test
        fun `an non-empty collection subject passes`() {
          fixture.run {
            isNotEmpty()
          }
        }
      }
    }
}
