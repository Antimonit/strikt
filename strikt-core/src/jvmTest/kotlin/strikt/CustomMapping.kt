package strikt

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDate

@DisplayName("custom mappings")
class CustomMapping {
  val subject = Person("David", LocalDate.of(1947, 1, 8))

  @Test
  fun `can map with a closure`() {
    expectThat(subject) {
      get { name }.isEqualTo("David")
      get { birthDate.year }.isEqualTo(1947)
    }
  }

  @Test
  fun `can map with property and method references`() {
    expectThat(subject) {
      get(Person::name).isEqualTo("David")
      get(Person::birthDate).get(LocalDate::getYear).isEqualTo(1947)
    }
  }

  @Test
  fun `closures can call methods`() {
    expectThat(subject) {
      get { name.uppercase() }.isEqualTo("DAVID")
      get { birthDate.plusYears(69).plusDays(2) }
        .isEqualTo(LocalDate.of(2016, 1, 10))
    }
  }

  @Test
  fun `can be described`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(subject)
          .describedAs { "a person named $name" }
          .and {
            get { name }.describedAs("name").isEqualTo("Ziggy")
            get { birthDate.year }.describedAs("birth year")
              .isEqualTo(1971)
          }
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that a person named David:
        |  ▼ name:
        |    ✗ is equal to "Ziggy"
        |            found "David"
        |  ▼ birth year:
        |    ✗ is equal to 1971
        |            found 1947
      """.trimMargin()
    )
  }

  @Test
  fun `descriptions are defaulted when using property references`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(subject).get(Person::name).isEqualTo("Ziggy")
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that Person(name=David, birthDate=1947-01-08):
        |  ▼ value of property name:
        |    ✗ is equal to "Ziggy"
        |            found "David"
      """.trimMargin()
    )
  }

  @Test
  fun `descriptions also default for blocks`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(subject) {
          get { name }.isEqualTo("Ziggy")
          get { birthDate.year }.isEqualTo(1971)
        }
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that Person(name=David, birthDate=1947-01-08):
        |  ▼ name:
        |    ✗ is equal to "Ziggy"
        |            found "David"
        |  ▼ birthDate.year:
        |    ✗ is equal to 1971
        |            found 1947
      """.trimMargin()
    )
  }

  @Test
  fun `descriptions are defaulted when using bean getter references`() {
    val error =
      assertThrows<AssertionError> {
        expectThat(subject).get(Person::birthDate)
          .get(LocalDate::getYear)
          .isEqualTo(1971)
      }
    expectThat(error.message).isEqualTo(
      """▼ Expect that Person(name=David, birthDate=1947-01-08):
        |  ▼ value of property birthDate:
        |    ▼ return value of getYear:
        |      ✗ is equal to 1971
        |              found 1947
      """.trimMargin()
    )
  }
}
