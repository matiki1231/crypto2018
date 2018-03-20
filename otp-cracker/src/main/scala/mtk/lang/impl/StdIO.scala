package mtk.lang.impl

import cats._
import cats.implicits._
import cats.effect.IO
import mtk.lang.Console

import scala.io.StdIn

class StdIO private() extends Console[IO] {

  override def write[A: Show](a: A): IO[Unit] = IO { print(a.show) }

  override def writeln[A: Show](a: A): IO[Unit] = IO { println(a.show) }

  override def readln(): IO[String] = IO { StdIn.readLine() }
}

object StdIO {

  implicit val instance: StdIO = new StdIO()
}
