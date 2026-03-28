package strikt.internal

internal expect object FilePeek {
  fun getCallerLambdaBody(): String?
}
