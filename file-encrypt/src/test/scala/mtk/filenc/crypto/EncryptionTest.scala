package mtk.filenc.crypto

import mtk.filenc.crypto.data.{IV, Key}
import org.scalatest._

import scala.util.Random

class EncryptionTest extends FeatureSpec with GivenWhenThen with Matchers {

  def randomBytes(length: Int): Array[Byte] = {
    val bytes = new Array[Byte](length)
    Random.nextBytes(bytes)
    bytes
  }

  scenario("Encrypting data with AES/CTR.") {
    Given("a random message, a key, an IV and AES/CTR block cipher")
    val message = randomBytes(53)
    val key = Key(randomBytes(16))
    val iv = IV(randomBytes(16))
    val cipher = new JavaBlockCipher("AES", BlockCipherMode.Ctr)

    When("this message is encrypted and decrypted")
    val messageAfter = cipher.decrypt(cipher.encrypt(message, key, iv), key)

    Then("the message after encryption and decryption should be exactly the same as before")
    messageAfter should equal (message)
  }

  scenario("Encrypting data with AES/CBC.") {
    Given("a random message, a key, an IV and AES/Cbc block cipher")
    val message = randomBytes(53)
    val key = Key(randomBytes(16))
    val iv = IV(randomBytes(16))
    val cipher = new JavaBlockCipher("AES", BlockCipherMode.Cbc)

    When("this message is encrypted and decrypted")
    val messageAfter = cipher.decrypt(cipher.encrypt(message, key, iv), key)

    Then("the message after encryption and decryption should be exactly the same as before")
    messageAfter should equal (message)
  }

  scenario("Encrypting data with AES/OFB.") {
    Given("a random message, a key, an IV and AES/OFB block cipher")
    val message = randomBytes(53)
    val key = Key(randomBytes(16))
    val iv = IV(randomBytes(16))
    val cipher = new JavaBlockCipher("AES", BlockCipherMode.Ofb)

    When("this message is encrypted and decrypted")
    val messageAfter = cipher.decrypt(cipher.encrypt(message, key, iv), key)

    Then("the message after encryption and decryption should be exactly the same as before")
    messageAfter should equal (message)
  }
}
