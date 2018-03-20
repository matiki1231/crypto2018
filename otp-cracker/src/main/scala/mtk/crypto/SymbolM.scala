package mtk.crypto

import cats._

trait SymbolM[S] {

}

object SymbolM {

//  implicit val monad = new Monad[SymbolM] {
//
//    override def pure[A](x: A): SymbolM[A] = ???
//
//    override def flatMap[A, B](fa: SymbolM[A])(f: A => SymbolM[B]): SymbolM[B] = ???
//
//    override def tailRecM[A, B](a: A)(f: A => SymbolM[Either[A, B]]): SymbolM[B] = ???
//  }

  case class Known[S: Symbol](symbol: S) extends SymbolM[S]

  case class Unknown[S: Symbol](symbol: S) extends SymbolM[S]
}
