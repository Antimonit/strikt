package strikt.gradle

import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

fun KotlinCommonCompilerOptions.commonCompilerOptions() {
  this.allWarningsAsErrors = true
}

fun KotlinJvmCompilerOptions.commonJvmCompilerOptions() {
  jvmTarget.set(JvmTarget.JVM_17)
  javaParameters = true
}
