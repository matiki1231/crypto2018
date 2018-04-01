package mtk.otpcracker.ui

import cats._
import cats.implicits._
import mtk.lang.Console

import scala.language.higherKinds

object ConsoleUI {

  val Header: String =
    """otp-cracker 0.1
      |Type 'help' for list of commands.
    """.stripMargin.trim

  val HelpMessage: String =
    """help
      |
      |exit
    """.stripMargin.trim

  def queryForInput[M[_]: Monad]()(implicit console: Console[M]): M[Unit] = for {
    _ <- console.write("> ")
    input <- console.readln()
    _ <- if (input != "exit") queryForInput[M]() else Monad[M].unit
  } yield ()

  def get[M[_]: Monad](implicit console: Console[M]): M[Unit] = for {
    _ <- console.writeln(Header)
    _ <- queryForInput[M]()
  } yield ()
}
