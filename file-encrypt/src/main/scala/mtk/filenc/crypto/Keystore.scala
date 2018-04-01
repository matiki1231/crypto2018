package mtk.filenc.crypto

import java.io.{File, FileInputStream}
import java.security.{UnrecoverableKeyException, KeyStore => JKeyStore}

import cats.effect.IO
import mtk.filenc.crypto.data.Key
import mtk.filenc.effect.ErrorIO

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class Keystore private(private val keystore: JKeyStore) extends AnyVal {

  def apply(alias: String, password: Array[Char]): ErrorIO[Key] = ErrorIO {
    try {
      keystore.getKey(alias, password) match {
        case null => Left(s"There is no entry for [$alias].")
        case key => Right(Key(key.getEncoded))
      }
    } catch {
      case e: UnrecoverableKeyException => Left(e.getMessage)
    }
  }

  def keyEntries: ErrorIO[Seq[String]] = ErrorIO.success {
    keystore.aliases().asScala.filter(keystore.isKeyEntry).toSeq
  }
}

object Keystore {

  def load(keystoreFile: File, password: Array[Char]): ErrorIO[Keystore] = ErrorIO {
    val keystore = JKeyStore.getInstance("jceks")
    try {
      keystore.load(new FileInputStream(keystoreFile), password)
      Right(new Keystore(keystore))
    } catch {
      case e => Left(e.getMessage)
    }
  }
}
