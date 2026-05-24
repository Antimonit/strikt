package strikt.internal

import java.io.File

private val ignoredPackages = listOf("strikt.internal", "strikt.api")

private val sourceRootMappings = listOf(
  "build/classes/kotlin/jvm/test" to "src/jvmTest/kotlin",
  "build/classes/kotlin/jvm/test" to "src/test/kotlin",
  "build/classes/kotlin/test" to "src/jvmTest/kotlin",
  "build/classes/kotlin/test" to "src/test/kotlin",
  "build/classes/java/jvmTest" to "src/jvmTest/java",
  "build/classes/java/test" to "src/jvmTest/java",
  "build/classes/java/test" to "src/test/java",
)

/**
 * Inspired by the FilePeek library. The original library does not support Kotlin Multiplatform
 * project layouts where test classes are compiled to `build/classes/kotlin/jvm/test` instead of
 * the conventional `build/classes/kotlin/test`.
 *
 * See https://github.com/christophsturm/filepeek
 */
internal actual fun getCallerLambdaBody(): String? = try {
  val stackTrace = RuntimeException().stackTrace
  val caller = stackTrace.first { el ->
    ignoredPackages.none { el.className.startsWith(it) }
  }

  val className = caller.className.substringBefore('$')
  val classLocation = File(Class.forName(className).protectionDomain.codeSource.location.path).absolutePath

  val sourceFile = sourceRootMappings
    .filter { (buildPath, _) -> classLocation.contains(buildPath) }
    .firstNotNullOfOrNull { (buildPath, sourcePath) ->
      File(classLocation.replace(buildPath, sourcePath))
        .resolve(className.replace('.', '/'))
        .parentFile
        .resolve(caller.fileName!!)
        .takeIf { it.exists() }
    } ?: return null

  val callerLine = sourceFile.bufferedReader().useLines { lines ->
    var delta = 0
    lines.drop(caller.lineNumber - 1)
      .takeWhileInclusive { line ->
        delta += line.count { it == '{' } - line.count { it == '}' }
        delta != 0
      }.joinToString("") { it.trim() }
  }

  lambdaBody("get", callerLine)
} catch (_: Exception) {
  null
}

private fun lambdaBody(methodName: String, line: String): String? {
  val firstBracket = line.indexOf('{', line.indexOf(methodName) + methodName.length) + 1
  if (firstBracket == 0)
    return null
  var level = 0
  var pos = firstBracket
  while (pos < line.length) {
    when (line[pos]) {
      '{' -> level++
      '}' -> if (level == 0) return line.substring(firstBracket, pos).trim() else level--
    }
    pos++
  }
  return null
}

private fun <T> Sequence<T>.takeWhileInclusive(pred: (T) -> Boolean): Sequence<T> {
  var shouldContinue = true
  return takeWhile {
    val result = shouldContinue
    shouldContinue = pred(it)
    result
  }
}
