package mtk.filenc

import cats.data.EitherT
import cats.effect.IO

package object effect {

  type ErrorIO[A] = EitherT[IO, String, A]
}
