package mtk.otpcracker.ui

trait Command

object Command {

  def parseCommand(raw: String): Option[Command] = ???

  case object Exit extends Command
  case object Help extends Command
  case class PlainText() extends Command
  case class CipherText() extends Command
}