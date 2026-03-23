---
hide:
  - navigation
  - toc
---

# ![Strikt](assets/logo.png)

**Strikt** is an assertion library for Kotlin intended for use with a test runner such as JUnit or Spek.

[Get Started](wiki/getting-started.md){ .md-button .md-button--primary }
[View on GitHub](https://github.com/robfletcher/strikt){ .md-button }

---

## A powerful fluent API

=== "Type-safe fluent assertions"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_one"
    ```

## Collection handling

=== "Flexible assertions about collections"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_two"
    ```

=== "\"Narrow\" the assertion to elements or ranges"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_three"
    ```

=== "Make grouping assertions"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_four"
    ```

## "Soft" assertions

=== "Use lambdas to execute multiple assertions on a subject at once…"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_five"
    ```

=== "…with structured diagnostics of those that fail"
    ```text
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_six"
    ```

=== "Use lambdas to execute assertions on multiple subjects at once…"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_seven"
    ```

## Strong typing

=== "Assertion functions can \"narrow\" the type of the assertion"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_eight"
    ```

=== "Assertions can \"map\" to properties and method results in a type safe way"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_nine"
    ```

## Extensibility

=== "Custom assertions are extension functions"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_ten"
    ```

=== "Custom mappings are extension properties"
    ```kotlin
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_eleven_a"
    --8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_eleven_b"
    ```
