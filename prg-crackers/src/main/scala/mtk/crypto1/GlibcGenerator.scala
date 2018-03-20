package mtk.crypto1

class GlibcGenerator {

  private val state = Array.ofDim[Long](344)
  private var counter = 0

  def init(seed: Int): Unit = {
    state(0) = seed.toLong & 0xFFFFFFFFL
    for (i <- 1 until 31) state(i) = (16807 * state(i - 1)) % 2147483647
    for (i <- 31 until 34) state(i) = state(i - 31)
    for (i <- 34 until 344) state(i) = state(i - 31) + state(i - 3)
  }

  def next(): Long = {
    state(counter % 344) = state((counter + 313) % 344) + state((counter + 341) % 344)
    val x = state(counter % 344)
    counter = (counter + 1) % 344
    (x >> 1) & 0x7FFFFFFFL
  }
}
