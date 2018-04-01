package mtk.filenc.challenge

import fastparse.all
import fastparse.all._
import mtk.filenc.io.Console
import mtk.filenc.effect._

import scala.language.higherKinds

trait Challenge[F[_]] {

  def readCommand[C <: ChallengeCommand](parser: P[C]): F[C]

  def matching[A, B](fa: F[A])(pf: PartialFunction[A, F[B]]): F[B]
}

object Challenge {

  implicit def challengeErrorIO(implicit console: Console[ErrorIO]) = new Challenge[ErrorIO] {

    override def readCommand[C <: ChallengeCommand](parser: all.P[C]): ErrorIO[C] = for {
      input <- console.readln()
      parsed <- parser.parse(input) match {
        case Parsed.Success(command, _) => ErrorIO.success(command)
        case f: Parsed.Failure => ErrorIO.failure(f.msg)
      }
    } yield parsed

    override def matching[A, B](fa: ErrorIO[A])(pf: PartialFunction[A, ErrorIO[B]]): ErrorIO[B] = for {
      value <- fa
      result <- pf(value)
    } yield result
  }
}