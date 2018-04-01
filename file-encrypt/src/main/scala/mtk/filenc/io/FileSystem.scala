package mtk.filenc.io

import java.io.{File, IOException}
import java.nio.file.Files

import mtk.filenc.effect.ErrorIO

import scala.language.higherKinds

trait FileSystem[F[_]] {

  def readBytes(file: File): F[Array[Byte]]

  def writeBytes(file: File, bytes: Array[Byte]): F[Unit]
}

object FileSystem {

  val fileSystemErrorIO = new FileSystem[ErrorIO] {

    override def readBytes(file: File): ErrorIO[Array[Byte]] = ErrorIO {
      try {
        Right(Files.readAllBytes(file.toPath))
      } catch {
        case e: IOException => Left(e.getMessage)
      }
    }

    override def writeBytes(file: File, bytes: Array[Byte]): ErrorIO[Unit] = ErrorIO {
      try {
        Right(Files.write(file.toPath, bytes))
      } catch {
        case e: IOException => Left(e.getMessage)
      }
    }
  }
}