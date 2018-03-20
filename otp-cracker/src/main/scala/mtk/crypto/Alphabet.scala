package mtk.crypto

case class Alphabet[S: Symbol](symbols: Seq[S]) {

  def generateXorLookup: Map[S, Seq[(S, S)]] = (for {
    s0 <- symbols
    s1 <- symbols
  } yield (s0, s1)).groupBy({ case (s0, s1) => s0 ⊕ s1 })

//  def ⊖(symbol: S)
}
