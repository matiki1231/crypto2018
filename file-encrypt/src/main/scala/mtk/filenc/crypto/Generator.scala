package mtk.filenc.crypto

import java.security.SecureRandom

import mtk.filenc.crypto.data.IV
import mtk.filenc.effect.ErrorIO

class Generator(incrementingMode: Boolean) {

  private val prng = new SecureRandom()
  private var previous = {
    val array = new Array[Byte](16)
    prng.nextBytes(array)
    array
  }

  def next(): ErrorIO[IV] = ErrorIO.success {
    if (incrementingMode) {
      previous = (BigInt(previous) + 1).toByteArray
      IV(previous)
    } else {
      val array = new Array[Byte](16)
      prng.nextBytes(array)
      IV(array)
    }
  }
}

object Generator {

  def apply(incrementingMode: Boolean): ErrorIO[Generator] =
    ErrorIO.success(new Generator(incrementingMode))
}