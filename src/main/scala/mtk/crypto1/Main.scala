package mtk.crypto1

object Main extends App {

//  val lcg = new LinearCongruentialGenerator(672257317069504227L, 7382843889490547368L, 9223372036854775783L)
//
//  lcg.init(2300417199649672133L)
//
//  val lcgc = new LcgCracker()
//
//  println("GENERATOR\t\tCRACKER\t\tRESULT")
//
//  for (i <- 0 until 100) {
//    val next = lcg.next()
//    val nextGuess = lcgc.guessNext()
//    lcgc.feedNext(next)
//    println(s"$next\t\t$nextGuess\t\t${if (next == nextGuess) "RIGHT" else "WRONG"}")
//  }

  val glibcg = new GlibcGenerator
  glibcg.init(21)

  val glibcgc = new GlibcGeneratorCracker

  for (i <- 1 to 100) {
    val next = glibcg.next()
    val nextGuess = glibcgc.guessNext()
    glibcgc.feedNext(next)
    println(s"$next\t\t$nextGuess\t\t${if (next == nextGuess) "RIGHT" else "WRONG"}")
  }
}
