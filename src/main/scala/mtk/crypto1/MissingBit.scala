package mtk.crypto1

trait MissingBit {

  def guessValue: Int
}

object MissingBit {

  case object Zero extends MissingBit {

    override def guessValue: Int = 0
  }

  case object One extends MissingBit {

    override def guessValue: Int = 1
  }

  object Known {

    def apply(value: Int): MissingBit =
      value match {
        case 0 => Zero
        case 1 => One
      }

    def unapply(missingBit: MissingBit): Option[Int] =
      missingBit match {
        case Zero => Some(0)
        case One => Some(1)
        case Unknown => None
      }
  }

  case object Unknown extends MissingBit {

    override def guessValue: Int = (math.random() * 2).toInt
  }

  def add(b0: MissingBit, b1: MissingBit, c: MissingBit): (MissingBit, MissingBit, MissingBit) = {
    (b0, b1, c) match {
      case (_, _, One) => (One, One, Zero)
      case (_, One, Zero) => (Zero, One, One)
      case (One, _, Zero) => (One, Zero, One)
      case (v, Zero, Zero) => (v, Zero, Zero)
      case (Zero, v, Zero) => (Zero, v, One)
    }
  }
}
