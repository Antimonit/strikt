package strikt.assertions

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.java.PersonJava

internal class PlatformTypes {

  @Test
  fun `when nullability is uncertain isNotNull can be applied`() {
    expectThat(PersonJava("Oswald Launcelot Campbell-Graves"))
      .get(PersonJava::getName)
      .isNotNull()
  }
}
