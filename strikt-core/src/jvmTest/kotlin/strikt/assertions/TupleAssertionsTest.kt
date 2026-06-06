package strikt.assertions

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat

internal class TupleAssertionsTest {
  @Nested
  inner class OnAPair {
    private val expectation = expectThat("a" to 1)

    @Test
    fun `first maps to component1`() {
      expectation.first.isEqualTo("a")
    }
    @Test
    fun `second maps to component2`() {
      expectation.second.isEqualTo(1)
    }
  }

  @Nested
  inner class OnATriple {
    private val triple = Triple("a", "b", 1)
    private val expectation = expectThat(triple)

    @Test
    fun `first maps to component1`() {
      expectation.first.isEqualTo("a")
    }
    @Test
    fun `second maps to component2`() {
      expectation.second.isEqualTo("b")
    }
    @Test
    fun `third maps to component3`() {
      expectation.third.isEqualTo(1)
    }
  }
}
