package mtk.filenc.crypto.data

import java.lang.Integer.parseInt

import scala.util.Try

class Key private(val bytes: Seq[Byte]) extends AnyVal {

  override def toString: String =
    s"Key(${bytes.map(b => f"$b%02x").mkString})"
}

object Key {

  def apply(bytes: Seq[Byte]): Key =
    new Key(bytes)

  def apply(hexString: String): Try[Key] = Try {
    Key(hexString
      .sliding(2, 2)
      .map(parseInt(_, 16).toByte)
      .toArray)
  }
}
