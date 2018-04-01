package mtk.filenc

import java.io.File

import cats._
import cats.implicits._
import fastparse.all._
import mtk.filenc.challenge.{Challenge, ChallengeCommand}
import mtk.filenc.crypto._
import mtk.filenc.crypto.data.Key
import mtk.filenc.effect.ErrorIO
import mtk.filenc.io.{Console, FileSystem}

import scala.language.higherKinds
import scala.util.Random

object Filenc {

  def program(args: FilencArgs)
             (implicit console: Console[ErrorIO],
              fileSystem: FileSystem[ErrorIO]): ErrorIO[Unit] = (for {
    key <- obtainKey(args.keystoreFile, args.challengeMode)
    cipher = new JavaBlockCipher("AES", args.blockCipherMode)
    generator <- Generator(args.incIvGen)
    _ <- args match {
      case _ if args.challengeMode => challengeMode(cipher, key, generator)
      case _ if args.encryptMode => foreachFile(args.files.toList)(encryptFile(_, cipher, key, generator))
      case _ if args.decryptMode => foreachFile(args.files.toList)(decryptFile(_, cipher, key))
    }
  } yield ()).leftFlatMap(errorMessage => console.writeln(s"Error: $errorMessage"))

  def challengeMode(cipher: BlockCipher, key: Key, generator: Generator)
                   (implicit challenge: Challenge[ErrorIO],
                    console: Console[ErrorIO]): ErrorIO[Unit] = {
    import challenge._
    import console._

    matching(readCommand(ChallengeCommand.EncryptParser | ChallengeCommand.ChallengeParser)) {
      case ChallengeCommand.Encrypt(bs) => for {
        iv <- generator.next()
        _ <- writeln(cipher.encrypt(bs, key, iv).map(b => f"$b%02x").mkString)
        _ <- challengeMode(cipher, key, generator)
      } yield ()
      case ChallengeCommand.Challenge(bs0, bs1) => for {
        challengeBit <- ErrorIO.success { Random.nextInt(2) }
        iv <- generator.next()
        bs = if (challengeBit == 0) bs0 else bs1
        _ <- writeln(cipher.encrypt(bs, key, iv).map(b => f"$b%02x").mkString)
        guess <- readCommand(ChallengeCommand.GuessParser)
        _ <- writeln(if (guess.challengeBit == challengeBit) "RIGHT" else "WRONG")
      } yield ()
    }
  }

  def foreachFile[F[_]: Foldable](files: F[File])(action: File => ErrorIO[Unit]): ErrorIO[Unit] =
    files.traverse_[ErrorIO, Unit](action)

  def encryptFile(file: File, cipher: BlockCipher, key: Key, generator: Generator)
                 (implicit fileSystem: FileSystem[ErrorIO]): ErrorIO[Unit] = for {
    bytes <- fileSystem.readBytes(file)
    iv <- generator.next()
    encrypted = cipher.encrypt(bytes, key, iv)
    _ <- fileSystem.writeBytes(new File(file.getAbsolutePath + ".enc"), encrypted)
  } yield ()

  def decryptFile(file: File, cipher: BlockCipher, key: Key)
                 (implicit fileSystem: FileSystem[ErrorIO]): ErrorIO[Unit] = for {
    bytes <- fileSystem.readBytes(file)
    decrypted = cipher.decrypt(bytes, key)
    _ <- fileSystem.writeBytes(new File(file.getAbsolutePath.stripSuffix(".enc")), decrypted)
  } yield ()

  def obtainKey(keystoreFile: File, silent: Boolean)
               (implicit console: Console[ErrorIO]): ErrorIO[Key] = for {
    _ <- console.iff(!silent) { console.write("Keystore password: ") }
    keystorePassword <- console.readPassword()

    keystore <- Keystore.load(keystoreFile, keystorePassword)
    keyEntries <- keystore.keyEntries.subflatMap {
      case Seq() => Left("No keys available.")
      case keys => Right(keys)
    }

    _ <- console.iff(!silent) { console.writeln("Available keys:") }
    _ <- console.iff(!silent) { console.writeln(keyEntries.mkString("\n")) }
    _ <- console.iff(!silent) { console.write("Key entry: ") }
    alias <- console.readln()

    _ <- console.iff(!silent) { console.write("Key password: ") }
    keyPassword <- console.readPassword()

    key <- keystore.apply(alias, keyPassword)
  } yield key
}
