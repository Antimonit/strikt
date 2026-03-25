---
hide:
  - navigation
  - toc
---

# ![Strikt](assets/logo.png)

<div class="homepage-version" markdown>
[Version {{ version }}](changelog.md)
</div>

<div class="homepage-intro" markdown>

Strikt is an assertion library for Kotlin intended for use with a test runner such as [JUnit](https://junit.org/junit5/), [Minutest](https://github.com/dmcg/minutest) or [Spek](http://spekframework.org/).

Strikt gives you…

</div>

<div class="homepage-feature" markdown>
<div class="homepage-feature-title" markdown>
## A powerful fluent API
</div>
<div class="homepage-feature-label" markdown>
Type-safe fluent assertions
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_one"
```
</div>
</div>

<div class="homepage-feature" markdown>
<div class="homepage-feature-title" markdown>
## Collection handling
</div>
<div class="homepage-feature-label" markdown>
Flexible assertions about collections
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_two"
```
</div>
<div class="homepage-feature-label" markdown>
"Narrow" the assertion to elements or ranges
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_three"
```
</div>
<div class="homepage-feature-label" markdown>
Make grouping assertions
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_four"
```
</div>
</div>

<div class="homepage-feature" markdown>
<div class="homepage-feature-title" markdown>
## "Soft" assertions
</div>
<div class="homepage-feature-label" markdown>
Use lambdas to execute multiple assertions on a subject at once…
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_five"
```
</div>
<div class="homepage-feature-label" markdown>
…with structured diagnostics of those that fail
</div>
<div class="homepage-feature-code" markdown>
```text
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_six"
```
</div>
<div class="homepage-feature-label" markdown>
Use lambdas to execute assertions on multiple subjects at once…
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_seven"
```
</div>
</div>

<div class="homepage-feature" markdown>
<div class="homepage-feature-title" markdown>
## Strong typing
</div>
<div class="homepage-feature-label" markdown>
Assertion functions can "narrow" the type of the assertion
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_eight"
```
</div>
<div class="homepage-feature-label" markdown>
Assertions can "map" to properties and method results in a type safe way
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_nine"
```
</div>
</div>

<div class="homepage-feature" markdown>
<div class="homepage-feature-title" markdown>
## Extensibility
</div>
<div class="homepage-feature-label" markdown>
Custom assertions are extension functions
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_ten"
```
</div>
<div class="homepage-feature-label" markdown>
Custom mappings are extension properties
</div>
<div class="homepage-feature-code" markdown>
```kotlin
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_eleven_a"
--8<-- "strikt-core/src/test/kotlin/strikt/docs/Homepage.kt:homepage_eleven_b"
```
</div>
</div>
