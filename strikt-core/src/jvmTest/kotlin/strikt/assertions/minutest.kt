package strikt.assertions

import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import strikt.api.Assertion
import java.util.stream.Stream

fun Any?.quoted(): String =
  when (this) {
    null -> "null"
    else -> "\"$this\""
  }

// Minimal DSL to produce JUnit5 DynamicNode trees, used by @TestFactory methods.
class AssertionTestBuilder<F : Any?> {
  private val nodes = mutableListOf<DynamicNode>()
  private var _factory: (() -> F)? = null

  fun fixture(factory: () -> F) {
    _factory = factory
  }

  fun test(name: String, block: F.() -> Unit) {
    val factory = requireNotNull(_factory) { "No fixture defined for test \"$name\"" }
    nodes += DynamicTest.dynamicTest(name) { factory().block() }
  }

  fun context(name: String, block: AssertionTestBuilder<F>.() -> Unit) {
    val child = AssertionTestBuilder<F>()
    child._factory = _factory
    child.block()
    nodes += DynamicContainer.dynamicContainer(name, child.nodes.stream())
  }

  fun <G : Any?> derivedContext(name: String, block: AssertionTestBuilder<G>.() -> Unit) {
    val child = AssertionTestBuilder<G>()
    child.block()
    nodes += DynamicContainer.dynamicContainer(name, child.nodes.stream())
  }

  internal fun build(): Stream<DynamicNode> = nodes.stream()
}

fun <S : Any?> assertionTests(
  block: AssertionTestBuilder<Assertion.Builder<S>>.() -> Unit
): Stream<DynamicNode> =
  AssertionTestBuilder<Assertion.Builder<S>>().also(block).build()
