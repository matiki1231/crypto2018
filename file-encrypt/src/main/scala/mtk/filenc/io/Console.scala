package mtk.filenc.io

import mtk.filenc.effect.ErrorIO

import scala.io.StdIn
import scala.language.higherKinds

trait Console[F[_]] {

  def iff(cond: => Boolean)(action: => F[Unit]): F[Unit]

  def write(str: String): F[Unit]

  def writeln(line: String): F[Unit]

  def readln(): F[String]

  def readPassword(): F[Array[Char]]
}

object Console {

  val consoleErrorIO = new Console[ErrorIO] {

    override def iff(cond: => Boolean)(action: => ErrorIO[Unit]): ErrorIO[Unit] = if (cond) action else ErrorIO.unit

    override def write(str: String): ErrorIO[Unit] = ErrorIO.success { print(str) }

    override def writeln(str: String): ErrorIO[Unit] = ErrorIO.success { println(str) }

    override def readln(): ErrorIO[String] = ErrorIO.success { StdIn.readLine() }

    override def readPassword(): ErrorIO[Array[Char]] = ErrorIO.success {
      if (System.console() != null) System.console.readPassword() else StdIn.readLine().toCharArray
    }
  }
}