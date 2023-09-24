package vmiroshka

import cats.effect._
import cats.Show.catsShowForString
import fs2._
import fs2.io.file._
import vmiroshka.MinimalPathFinder.DijkstraPathFinder
import vmiroshka.Parser.SpaceSplitParser
import vmiroshka.Reader.{ConsoleReader, FileReader}


object Main extends IOApp.Simple {

  override def run: IO[Unit] = {
    new FileReader[IO](Path.fromNioPath(java.nio.file.Paths.get("")))
      .readLines
      .filter(_.nonEmpty)
      .through(new SpaceSplitParser[IO].parseDataLinesPipe())
      .through(new DijkstraPathFinder().findMinimalPathPipe())
      .evalTap(treePath => IO.println(s"Minimal path is: ${treePath.nodes.mkString(" + ")} = ${treePath.cost}"))
      .compile
      .drain
  }
}