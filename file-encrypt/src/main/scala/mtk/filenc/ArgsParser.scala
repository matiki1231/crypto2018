package mtk.filenc

import java.io.File

import mtk.filenc.crypto.BlockCipherMode
import scopt.OptionParser

case class FilencArgs(challengeMode: Boolean = false,
                      encryptMode: Boolean = false,
                      decryptMode: Boolean = false,
                      incIvGen: Boolean = false,
                      keystoreFile: File = new File(""),
                      blockCipherMode: BlockCipherMode = BlockCipherMode.None,
                      files: Seq[File] = Seq())

object ArgsParser extends OptionParser[FilencArgs]("filenc") {

  head("filenc", "0.1")

  opt[Unit]('c', "challenge")
    .text("when this is specified the program runs in challenge mode")
    .action((_, c) => c.copy(challengeMode = true))

  opt[Unit]('e', "encrypt")
    .text("when this is specified the program runs in encryption mode")
    .action((_, c) => c.copy(encryptMode = true))

  opt[Unit]('d', "decrypt")
    .text("when this is specified the program runs in decryption mode")
    .action((_, c) => c.copy(decryptMode = true))

  checkConfig(c =>
    if (Seq(c.challengeMode, c.encryptMode, c.decryptMode).count(identity) == 1) Right(())
    else Left("Exactly one mode can be specified (challenge|encrypt|decrypt)")
  )

  opt[Unit]('i', "inc-gen")
    .text("enables incrementing IV generator")
    .action((_, c) => c.copy(incIvGen = true))

  opt[File]('k', "keystore")
    .required()
    .valueName("[keystore-path]")
    .text("when this is specified encryption key is loaded from it")
    .action((f, c) => c.copy(keystoreFile = f))

  opt[BlockCipherMode]('m', "bcmode")
    .required()
    .valueName("<bcmode>")
    .text("this specifies the block cipher mode (CBC|CTR|OFB)")
    .action((m, c) => c.copy(blockCipherMode = m))

  arg[File]("[files]...")
    .optional()
    .unbounded()
    .text("files to be encrypted")
    .action((f, c) => c.copy(files = c.files :+ f))

  help("help").abbr("h")
    .text("displays this usage text")
}
