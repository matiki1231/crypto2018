package mtk.filenc.challenge

import fastparse.all._

sealed trait ChallengeCommand

object ChallengeCommand {

  case class Encrypt(bytes: Array[Byte]) extends ChallengeCommand
  case class Challenge(bytes0: Array[Byte], bytes1: Array[Byte]) extends ChallengeCommand
  case class Guess(challengeBit: Int) extends ChallengeCommand

  val HexString = CharsWhileIn(('0' to '9') ++ ('a' to 'f') ++ ('A' to 'F')).rep.! map {
    _.sliding(2, 2).map(Integer.parseInt(_).toByte).toArray
  }

  val EncryptParser: P[Encrypt] = P(("encrypt" ~ " ".rep ~ HexString).map { Encrypt })

  val ChallengeParser: P[Challenge] = P(("challenge" ~ " ".rep ~ HexString ~ " ".rep ~ HexString).map {
    case (bs0, bs1) => Challenge(bs0, bs1)
  })

  val ChallengeBit = CharIn("01").! map { Integer.parseInt(_) }

  val GuessParser: P[Guess] = P(("guess" ~ " ".rep ~ ChallengeBit).map { Guess })
}
