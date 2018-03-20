package mtk.otpcracker

import cats._
import cats.implicits._
import fastparse.core._
import mtk.lang.Console

import scala.language.higherKinds

object ConsoleUI {

  val Header: String =
    """otp-cracker 0.1
      |Type 'help' for list of commands.
    """.stripMargin

  val HelpMessage: String =
    """help
      |
      |exit
    """.stripMargin

  def get[M[_]: Monad](implicit console: Console[M]): M[Unit] = for {
    _ <- console.writeln()
    _ <- console.writeln("")
    _ <- console.write("> ")
  } yield ()
}
