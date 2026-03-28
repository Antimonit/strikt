package strikt.internal

import filepeek.FilePeek as FilePeekLib
import filepeek.LambdaBody

private val filePeek by lazy {
  FilePeekLib(
    listOf(
      "strikt.internal",
      "strikt.api",
      "filepeek"
    )
  )
}

internal actual fun getCallerLambdaBody(): String? =
  try {
    val line = filePeek.getCallerFileInfo().line
    LambdaBody("get", line).body.trim()
  } catch (_: Exception) {
    null
  }
