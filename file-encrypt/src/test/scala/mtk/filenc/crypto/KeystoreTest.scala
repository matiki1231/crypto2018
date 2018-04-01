package mtk.filenc.crypto

import java.io.File

import mtk.filenc.crypto.data.Key
import org.scalatest._

class KeystoreTest extends FeatureSpec with GivenWhenThen with Matchers with EitherValues {

  scenario("Listing keys in a keystore.") {
    Given("a keystore with one key aliased as [key]")
    val keystoreFile = new File(getClass.getResource("/test_keystore.jceks").toURI)
    val password = "password".toCharArray

    When("keys in the keystore are listed")
    val keys = (for {
      keystore <- Keystore.load(keystoreFile, password)
      keys <- keystore.keyEntries
    } yield keys).value.unsafeRunSync()

    Then("the key list should contain exactly one key aliased as [key]")
    keys shouldBe Right(Seq("key"))
  }

  scenario("Obtaining a key from a keystore.") {
    Given("a keystore with one key")
    val keystoreFile = new File(getClass.getResource("/test_keystore.jceks").toURI)
    val password = "password".toCharArray
    val keyAlias = "key"
    val expectedKey = Key("236512db0da57d77868809a3c55051b8").get

    When("the key is loaded from the key store")
    val key = (for {
      keystore <- Keystore.load(keystoreFile, password)
      key <- keystore(keyAlias, password)
    } yield key).value.unsafeRunSync()

    Then("it should be successfully obtained")
    And("its value should be correct")
    key.right.value shouldBe expectedKey
  }
}
