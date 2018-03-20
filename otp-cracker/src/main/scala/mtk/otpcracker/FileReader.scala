package mtk.otpcracker

object FileReader {

  def binaryStringToByteArray(str: String): Array[Byte] =
    str.trim.split("\\s+").map(Integer.parseInt(_, 2).toByte)
}
