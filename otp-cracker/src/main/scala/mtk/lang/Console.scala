package mtk.lang

import cats._
import cats.implicits._

import scala.language.higherKinds

trait Console[F[_]] {

  def write[A: Show](a: A): F[Unit]

  def writeln[A: Show](a: A): F[Unit] =
    write(s"${a.show}\n")

  def readln(): F[String]
}