package strikt.internal

import filepeek.FilePeek
import filepeek.LambdaBody

internal object FilePeek {
  private val filePeek by lazy {
    FilePeek(
      listOf(
        "strikt.internal",
        "strikt.api",
        "filepeek"
      )
    )
  }

  fun getCallerLambdaBody(): String? =
    try {
      val line = filePeek.getCallerFileInfo().line
      LambdaBody("get", line).body.trim()
    } catch (_: Exception) {
      null
    }
}
