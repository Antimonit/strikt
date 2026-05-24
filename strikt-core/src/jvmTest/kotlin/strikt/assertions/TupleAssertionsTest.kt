package strikt.assertions

import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import strikt.api.expectThat

internal object TupleAssertionsTest : JUnit5Minutests {
  fun tests() =
    rootContext {
      context("on an expectation on a pair") {
        val expectation = expectThat("a" to 1)
        test("first maps to component1") {
          expectation.first.isEqualTo("a")
        }
        test("second maps to component2") {
          expectation.second.isEqualTo(1)
        }
      }
      context("on an expectation on a triple") {
        val triple = Triple("a", "b", 1)
        val expectation = expectThat(triple)
        test("first maps to component1") {
          expectation.first.isEqualTo("a")
        }
        test("second maps to component2") {
          expectation.second.isEqualTo("b")
        }
        test("third maps to component3") {
          expectation.third.isEqualTo(1)
        }
      }
    }
}
