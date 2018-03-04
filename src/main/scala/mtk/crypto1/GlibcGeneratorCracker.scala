package mtk.crypto1

class GlibcGeneratorCracker {

  private val state = Array.fill[Option[Long]](344)(None)
  private val missingBits = Array.fill[Option[MissingBit]](344)(None)
  private var counter = 0

  def guessNext(): Long =
    (for {
      feedback0 <- state((counter + 313) % 344)
      feedback0Lsb <- missingBits((counter + 313) % 344)
      feedback1 <- state((counter + 341) % 344)
      feedback1Lsb <- missingBits((counter + 341) % 344)
    } yield {
      ((feedback0 + feedback1 + feedback0Lsb.guessValue + feedback1Lsb.guessValue) >> 1) & 0x7FFFFFFF
    }).getOrElse(0)

  def feedNext(next: Long): Unit = {
    state(counter % 344) = Some(next << 1)
    missingBits(counter % 344) = Some(MissingBit.Unknown)
    for {
      feedback0 <- state((counter + 313) % 344)
      feedback0Lsb <- missingBits((counter + 313) % 344)
      feedback1 <- state((counter + 341) % 344)
      feedback1Lsb <- missingBits((counter + 341) % 344)
      result <- state(counter % 344)
    } yield {
      val carry = if (result != ((feedback0 + feedback1) & 0xFFFFFFFF)) MissingBit.One else MissingBit.Zero
      val (lsb0, lsb1, lsbr) = MissingBit.add(feedback0Lsb, feedback1Lsb, carry)

      missingBits((counter + 313) % 344) = Some(lsb0)
      missingBits((counter + 341) % 344) = Some(lsb1)
      missingBits(counter % 344) = Some(lsbr)
    }
    counter = (counter + 1) % 344
  }
}
