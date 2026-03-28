package strikt.internal

import filepeek.FilePeek as FilePeekLib
import filepeek.LambdaBody

internal actual object FilePeek {
  private val filePeek by lazy {
    FilePeekLib(
      listOf(
        "strikt.internal",
        "strikt.api",
        "filepeek"
      )
    )
  }

  actual fun getCallerLambdaBody(): String? =
    try {
      val line = filePeek.getCallerFileInfo().line
      LambdaBody("get", line).body.trim()
    } catch (_: Exception) {
      null
    }
}
