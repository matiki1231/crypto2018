package mtk.otpcracker.ui

import fastparse.all._

object CommandParser {

  val IndexExpr: P[Seq[Int]] = {
    val Single = (CharIn('1' to '9') ~ CharsWhileIn('0' to '9').rep | "0").! map { s => Integer.parseInt(s) }
    val Range = Single ~ ".." ~ Single map { case (i0, i1) => i0 to i1 }
    val List = ("[" ~ Single ~ (" ".rep ~ Single).rep ~ "]") map { case (h, t) => h +: t }
    val Wildcard = "*".! map { _ => Stream.from(0) }

    Range | List | Wildcard | Single.map(Seq(_))
  }

  val Exit: P[Command.Exit.type] = "exit".! map { _ => Command.Exit }
  val Help: P[Command.Help.type] = "help".! map { _ => Command.Help }
}
