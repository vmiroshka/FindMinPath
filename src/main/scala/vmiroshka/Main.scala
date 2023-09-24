package vmiroshka

import cats.effect._
import cats.Show.catsShowForString
import fs2._
import fs2.io.file._


object Main extends IOApp.Simple {

  override def run: IO[Unit] = {
    val pathToFile = "src/main/resources/data_big.txt"

    val path = Path.fromNioPath(java.nio.file.Paths.get(pathToFile))

    def processLine(resultAccumulator: ResultAccumulator, treeLine: Vector[Int]): ResultAccumulator = {
      if (treeLine.length == 1) {
        ResultAccumulator(Vector(TreePath.head(treeLine.head, 0)))
      } else {
        val newPaths = resultAccumulator
          .paths
          .flatMap { path =>
            val leftPath = path.addNode(treeLine(path.lastIndexInLevel), path.lastIndexInLevel)
            val rightPath = path.addNode(treeLine(path.lastIndexInLevel + 1), path.lastIndexInLevel + 1)
            Vector(leftPath, rightPath)
          }
          .groupBy(_.lastIndexInLevel)
          .map { case (_, paths) => paths.minBy(_.cost) }
          .toVector

        ResultAccumulator(newPaths)
      }
    }

    def decodeTreeLine(str: String): Vector[Int] =
      str.split(" ").map(_.toInt).toVector


    val handler: Pipe[IO, Vector[Int], TreePath] = src =>
      src
        .fold(ResultAccumulator.empty)(processLine)
        .map(_.paths.minBy(_.cost))

    Files[IO]
      .readUtf8Lines(path)
      .filter(_.nonEmpty)
      .map(decodeTreeLine)
      .through(handler)
      .evalTap(treePath => IO.println(s"Minimal path is: ${treePath.nodes.mkString(" + ")} = ${treePath.cost}"))
      .compile
      .drain
  }
}

//Write a command-line program that reads a text-format triangle
//
//from standard input and outputs a minimal path to the standard-
//  output as follows: 4 4 Besides producing correct answers
//
//your code should
//1. use functional concepts - i.e. think
//higher order functions
//2. be capable of producing the correct
//answer for 500-row triangle
//
//$ cat << EOF | java MinTrianglePath
//> 7
//> 6 3
//> 3 8 5
//> 11 2 10 9
//> EOF
//  Minimal path is: 7 + 6 + 3 + 2 = 18