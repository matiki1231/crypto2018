package mtk.crypto1

object GlibcTest extends App {

  val glibcg = new GlibcGenerator
  glibcg.init(21)

  val glibcgc = new GlibcGeneratorCracker
  
  println("GENERATOR\t\tCRACKER\t\tRESULT")

  for (i <- 1 to 1000) {
    val next = glibcg.next()
    val nextGuess = glibcgc.guessNext()
    glibcgc.feedNext(next)
    println(s"$next\t\t$nextGuess\t\t${if (next == nextGuess) "RIGHT" else "WRONG"}")
  }
}
