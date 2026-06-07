package strikt.assertions

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows

internal class Assertions {

  @Test
  fun `exceptions are suppressed`() {
    expectThrows<AssertionError> {
      expectThat(false).isEqualTo(true)
    }
      .and {
        get { stackTrace.toList() }
          .isNotEmpty()
          .map { it.className }
          .none {
            startsWith("strikt")
          }
        get { suppressed.toList() }
          .hasSize(1)
          .single()
          .get { stackTrace.toList() }
          .isNotEmpty()
          .map { it.className }
          .any {
            startsWith("strikt")
          }
      }
  }
}
