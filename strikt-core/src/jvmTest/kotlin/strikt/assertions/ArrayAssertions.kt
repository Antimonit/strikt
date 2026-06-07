package strikt.assertions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import strikt.api.expectThat
import strikt.internal.reporting.toHex
import java.lang.System.nanoTime
import java.util.Random

internal class ArrayAssertions {

  @Test
  fun `contentEquals passes when byte array contents equal a copy`() {
    val subject = randomBytes()
    expectThat(subject).contentEquals(subject.copyOf())
  }

  @Test
  fun `contentEquals fails when byte array contents are different`() {
    val subject = randomBytes()
    val expected = randomBytes()
    val error = assertThrows<AssertionError> {
      expectThat(subject).contentEquals(expected)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that 0x${subject.toHex()}:
        |  ✗ array content equals 0x${expected.toHex()}"""
        .trimMargin()
    )
  }

  @Test
  fun `contentEquals fails when byte array contents are a sub-array`() {
    val subject = randomBytes()
    val expected = subject.copyOf(subject.size / 2)
    val error = assertThrows<AssertionError> {
      expectThat(subject).contentEquals(expected)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that 0x${subject.toHex()}:
        |  ✗ array content equals 0x${expected.toHex()}"""
        .trimMargin()
    )
  }

  @Test
  fun `isEqualTo passes when byte array is equal to a copy`() {
    val subject = randomBytes()
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEqualTo fails for a different byte array`() {
    val subject = randomBytes()
    val expected = randomBytes()
    val error = assertThrows<AssertionFailedError> {
      expectThat(subject).isEqualTo(expected)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that 0x${subject.toHex()}:
        |  ✗ is equal to 0x${expected.toHex()}
        |          found 0x${subject.toHex()}"""
        .trimMargin()
    )
  }

  @Test
  fun `isEqualTo fails when expected byte array is null`() {
    assertThrows<AssertionFailedError> {
      expectThat(randomBytes()).isEqualTo(null)
    }
  }

  @Test
  fun `isEmpty fails when the byte array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(randomBytes()).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the byte array is empty`() {
    expectThat(byteArrayOf()).isEmpty()
  }

  @Test
  fun `contentEquals passes when char array contents equal a copy`() {
    val subject = "fnord".toCharArray()
    expectThat(subject).contentEquals(subject.copyOf())
  }

  @Test
  fun `contentEquals fails when char array contents are different`() {
    val subject = "fnord".toCharArray()
    val expected = "discord".toCharArray()
    val error = assertThrows<AssertionError> {
      expectThat(subject).contentEquals(expected)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ${subject.toList().map { "'$it'" }}:
        |  ✗ array content equals ${expected.toList().map { "'$it'" }}"""
        .trimMargin()
    )
  }

  @Test
  fun `contentEquals fails when char array contents are a sub-array`() {
    val subject = "fnord".toCharArray()
    val expected = subject.copyOf(subject.size / 2)
    val error = assertThrows<AssertionError> {
      expectThat(subject).contentEquals(expected)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ${subject.toList().map { "'$it'" }}:
        |  ✗ array content equals ${expected.toList().map { "'$it'" }}"""
        .trimMargin()
    )
  }

  @Test
  fun `isEqualTo passes when char array is equal to a copy`() {
    val subject = "fnord".toCharArray()
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEqualTo fails for a different char array`() {
    val subject = "fnord".toCharArray()
    val expected = "discord".toCharArray()
    val error = assertThrows<AssertionFailedError> {
      expectThat(subject).isEqualTo(expected)
    }
    expectThat(error.message).isEqualTo(
      """▼ Expect that ${subject.toList().map { "'$it'" }}:
        |  ✗ is equal to ${expected.toList().map { "'$it'" }}
        |          found ${subject.toList().map { "'$it'" }}"""
        .trimMargin()
    )
  }

  @Test
  fun `isEqualTo fails when expected char array is null`() {
    assertThrows<AssertionFailedError> {
      expectThat("fnord".toCharArray()).isEqualTo(null)
    }
  }

  @Test
  fun `isEmpty fails when the char array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat("fnord".toCharArray()).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the char array is empty`() {
    expectThat(charArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when boolean array is equal to a copy`() {
    val subject = BooleanArray(8) { it % 2 == 0 }
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the boolean array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(BooleanArray(8) { it % 2 == 0 }).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the boolean array is empty`() {
    expectThat(booleanArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when short array is equal to a copy`() {
    val subject = shortArrayOf(1, 2, 4, 8, 16, 32, 64, 128)
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the short array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(shortArrayOf(1, 2, 4, 8, 16, 32, 64, 128)).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the short array is empty`() {
    expectThat(shortArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when int array is equal to a copy`() {
    val subject = intArrayOf(1, 2, 4, 8, 16, 32, 64, 128)
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the int array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(intArrayOf(1, 2, 4, 8, 16, 32, 64, 128)).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the int array is empty`() {
    expectThat(intArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when long array is equal to a copy`() {
    val subject = longArrayOf(1, 2, 4, 8, 16, 32, 64, 128)
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the long array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(longArrayOf(1, 2, 4, 8, 16, 32, 64, 128)).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the long array is empty`() {
    expectThat(longArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when float array is equal to a copy`() {
    val subject = floatArrayOf(4.2f, 128.3f, 64.5f, 32.9f)
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the float array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(floatArrayOf(4.2f, 128.3f, 64.5f, 32.9f)).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the float array is empty`() {
    expectThat(floatArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when double array is equal to a copy`() {
    val subject = doubleArrayOf(4.2, 128.3, 64.5, 32.9)
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the double array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(doubleArrayOf(4.2, 128.3, 64.5, 32.9)).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the double array is empty`() {
    expectThat(doubleArrayOf()).isEmpty()
  }

  @Test
  fun `isEqualTo passes when object array is equal to a copy`() {
    val subject = arrayOf("catflap", "rubberplant", "marzipan")
    expectThat(subject).isEqualTo(subject.copyOf())
  }

  @Test
  fun `isEmpty fails when the object array is not empty`() {
    assertThrows<AssertionFailedError> {
      expectThat(arrayOf("catflap", "rubberplant", "marzipan")).isEmpty()
    }
  }

  @Test
  fun `isEmpty passes when the object array is empty`() {
    expectThat(arrayOf<Any>()).isEmpty()
  }

  @Test
  fun `can map an array to a list for easier matching`() {
    val array = arrayOf("catflap", "rubberplant", "marzipan")
    expectThat(array).toList().first().isEqualTo("catflap")
  }
}

internal fun randomBytes(): ByteArray = ByteArray(8).also(Random(nanoTime())::nextBytes)
