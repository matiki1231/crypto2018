package mtk.filenc.effect

import cats.data.EitherT
import cats.effect.IO

object ErrorIO {

  def apply[A](a: => Either[String, A]): ErrorIO[A] =
    EitherT(IO(a))

  def failure(reason: String): ErrorIO[Nothing] =
    EitherT.leftT(reason)

  def success[A](a: => A): ErrorIO[A] =
    EitherT[IO, String, A](IO { Right(a) })

  def lift[A](io: IO[A]): ErrorIO[A] =
    EitherT.liftF(io)

  def lift[A](either: Either[String, A]): ErrorIO[A] =
    EitherT(IO.pure(either))

  def unit: ErrorIO[Unit] =
    EitherT.liftF(IO.unit)
}
