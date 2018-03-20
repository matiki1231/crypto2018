package mtk.crypto1

class LinearCongruentialGenerator(mul: BigInt, inc: BigInt, mod: BigInt) {

  private var state: BigInt = 0

  def init(seed: BigInt): Unit = state = seed

  def next(): BigInt = {
    state = (mul * state + inc) % mod
    state
  }
}
