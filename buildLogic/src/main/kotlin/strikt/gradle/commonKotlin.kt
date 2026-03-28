package strikt.gradle

import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0

fun KotlinCommonCompilerOptions.commonCompilerOptions() {
  languageVersion.set(KOTLIN_2_0)
  freeCompilerArgs = listOf("-Xjvm-default=all")
  allWarningsAsErrors = true
}
