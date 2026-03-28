package strikt.gradle

plugins {
  id("org.jmailen.kotlinter")
}

// Lint Kotlin code
kotlinter {
  ignoreFailures = true
  // indentSize = 2
  reporters = arrayOf("html", "plain")
}
