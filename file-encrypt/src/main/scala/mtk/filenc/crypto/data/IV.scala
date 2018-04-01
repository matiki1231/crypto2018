package mtk.filenc.crypto.data

class IV private (val bytes: Seq[Byte]) extends AnyVal {

  override def toString: String =
    s"IV(${bytes.map(b => f"$b%02x").mkString})"
}

object IV {

  def apply(bytes: Seq[Byte]): IV =
    new IV(bytes)
}