package mtk.filenc.crypto

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

import mtk.filenc.crypto.data.{IV, Key}

class JavaBlockCipher(algorithm: String, val mode: BlockCipherMode) extends BlockCipher {

  override def encrypt(plaintext: Array[Byte], key: Key, iv: IV): Array[Byte] = {
    val cipher = Cipher.getInstance(s"$algorithm/$mode/PKCS5Padding")

    require(iv.bytes.size == cipher.getBlockSize)
    cipher.getBlockSize

    val keySpec = new SecretKeySpec(key.bytes.toArray, algorithm)
    val ivSpec = new IvParameterSpec(iv.bytes.toArray)

    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    iv.bytes.toArray ++ cipher.doFinal(plaintext)
  }

  override def decrypt(ciphertext: Array[Byte], key: Key): Array[Byte] = {
    val cipher = Cipher.getInstance(s"$algorithm/$mode/PKCS5Padding")

    val iv = ciphertext.take(cipher.getBlockSize)
    val keySpec = new SecretKeySpec(key.bytes.toArray, algorithm)
    val ivSpec = new IvParameterSpec(iv)

    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(ciphertext.drop(cipher.getBlockSize))
  }
}
