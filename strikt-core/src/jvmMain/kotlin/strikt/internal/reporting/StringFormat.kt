package strikt.internal.reporting

import kotlin.text.format as kotlinFormat

internal actual fun String.format(value: Any?): String = kotlinFormat(value)
