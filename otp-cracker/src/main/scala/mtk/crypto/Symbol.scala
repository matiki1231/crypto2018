package mtk.crypto

import simulacrum.{op, typeclass}

@typeclass trait Symbol[S] {

  @op("⊕") def xor(symbol0: S, symbol1: S): S

  def ixor(alphabet: Seq[S], symbol: S): Seq[S] =
    alphabet.map(xor(_, symbol))
}

object Symbol {

  implicit val byteSymbol: Symbol[Byte] = new Symbol[Byte] {

    override def xor(symbol0: Byte, symbol1: Byte): Byte = (symbol0 ^ symbol1).toByte
  }
}