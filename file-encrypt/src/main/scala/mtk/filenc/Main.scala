package mtk.filenc

import mtk.filenc.io.{Console, FileSystem}

object Main extends App {

  ArgsParser.parse(args, FilencArgs()) foreach { args =>
    Filenc.program(args)(Console.consoleErrorIO, FileSystem.fileSystemErrorIO).value.unsafeRunSync()
  }
}
