package mtk.otpcracker

import java.io.File

import cats.effect.IO
import mtk.otpcracker.ui.ConsoleUI
import scopt.OptionParser


object Main2 extends App {

  case class Args(input: Option[File] = None)

  object ArgsParser extends OptionParser[Args]("otp-cracker") {
    head("otp-cracker", "0.1")

    opt[File]('i', "input")
      .action({ case (input, args) => args.copy(input = Some(input)) })
      .valueName("<input>")
      .text("input file with ciphertexts")

    help("help").text("prints this usage text")
  }

  import mtk.lang.impl.StdIO._

  ArgsParser.parse(args, Args()) match {
    case Some(parsedArgs) => ConsoleUI.get[IO].unsafeRunSync()
    case None => ArgsParser.showUsage()
  }
}
