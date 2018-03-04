package mtk.crypto1

import scala.collection.mutable
import scala.util.Try
//private var mul: Guess[BigInt],
//private var inc: Guess[BigInt],
//private var mod: Guess[BigInt]
class LcgCracker(private var mod: BigInt = 0) {

  private var inc: Option[BigInt] = None
  private var mul: Option[BigInt] = None

  private var diff0: BigInt = 0
  private var diff1: BigInt = 0
  private var diff2: BigInt = 0

  private var previous: BigInt = 0

  def guessNext(): BigInt = {
    if (mod == 0) 0
    else (for {
      m <- mul
      i <- inc
    } yield ((previous * m + i) % mod + mod) % mod).getOrElse(0)
  }

  def feedNext(next: BigInt): Unit = {
    calculateNextDiffs(next)
    if (diff0 != 0) {
      val newMod = mod.gcd(diff2 * diff0 - diff1 * diff1)

      if (newMod != mod) {
        mul = Try((diff2 * diff1.modInverse(newMod)) % newMod).toOption orElse mul
        inc = mul.map(m => (next - m * previous) % mod) orElse inc
      } else {
        mul = mul orElse Try((diff2 * diff1.modInverse(newMod)) % newMod).toOption
        inc = inc orElse mul.map(m => (next - m * previous) % mod)
      }

      mod = newMod
    }
    previous = next
  }

  private def calculateNextDiffs(next: BigInt): Unit = {
    if (previous != 0) {
      diff0 = diff1
      diff1 = diff2
      diff2 = next - previous
    }
  }
}
