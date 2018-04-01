package mtk.filenc.crypto

import scopt.Read

sealed trait BlockCipherMode {

  override def toString: String = getClass.getSimpleName.toUpperCase.dropRight(1)
}

object BlockCipherMode {

  case object None extends BlockCipherMode
  case object Ofb extends BlockCipherMode
  case object Ctr extends BlockCipherMode
  case object Cbc extends BlockCipherMode

  implicit object BlockCipherModeRead extends Read[BlockCipherMode] {

    override def arity: Int = 1

    override def reads: String => BlockCipherMode = {
      case "OFB" => Ofb
      case "CTR" => Ctr
      case "CBC" => Cbc
    }
  }
}