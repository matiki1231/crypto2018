package mtk.filenc.crypto

import mtk.filenc.crypto.data.{IV, Key}

trait BlockCipher {

  val mode: BlockCipherMode

  def encrypt(plaintext: Array[Byte], key: Key, iv: IV): Array[Byte]

  def decrypt(ciphertext: Array[Byte], key: Key): Array[Byte]
}
