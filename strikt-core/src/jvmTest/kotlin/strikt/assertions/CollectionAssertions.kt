package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat

internal class CollectionAssertions {

  @Test
  fun `hasSize passes when the collection is the expected size`() {
    expectThat(setOf("catflap", "rubberplant", "marzipan")).hasSize(3)
  }

  @Test
  fun `hasSize fails when the collection is not the expected size`() {
    assertThrows<AssertionError> {
      expectThat(setOf("catflap", "rubberplant", "marzipan")).hasSize(1)
    }
  }

  @Test
  fun `isEmpty passes when the collection is empty`() {
    expectThat(emptyList<Any>()).isEmpty()
  }

  @Test
  fun `isEmpty fails when the collection is not empty`() {
    assertThrows<AssertionError> {
      expectThat(listOf("catflap", "rubberplant", "marzipan")).isEmpty()
    }
  }

  @Test
  fun `isNotEmpty fails when the collection is empty`() {
    assertThrows<AssertionError> {
      expectThat(emptyList<Any>()).isNotEmpty()
    }
  }

  @Test
  fun `isNotEmpty passes when the collection is not empty`() {
    expectThat(listOf("catflap", "rubberplant", "marzipan")).isNotEmpty()
  }
}
