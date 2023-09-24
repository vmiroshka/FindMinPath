package vmiroshka

import cats.effect._
import cats.Show.catsShowForString
import vmiroshka.MinimalPathFinder.DijkstraPathFinder
import vmiroshka.Parser.SpaceSplitParser
import vmiroshka.Reader.ConsoleReader


object Main extends IOApp.Simple {

  override def run: IO[Unit] = {
    new ConsoleReader[IO]()
      .readLines
      .filter(_.nonEmpty)
      .through(new SpaceSplitParser[IO].parseDataLinesPipe())
      .through(new DijkstraPathFinder().findMinimalPathPipe())
      .evalTap(treePath => IO.println(s"Minimal path is: ${treePath.nodes.mkString(" + ")} = ${treePath.cost}"))
      .compile
      .drain
  }
}