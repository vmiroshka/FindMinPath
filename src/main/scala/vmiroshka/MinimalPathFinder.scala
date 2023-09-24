package vmiroshka

import fs2._

trait MinimalPathFinder[F[_]] {
  def findMinimalPathPipe(): Pipe[F, Vector[Int], TreePath]
}

object MinimalPathFinder {

  class DijkstraPathFinder[F[_]] extends MinimalPathFinder[F] {
    def findMinimalPathPipe(): Pipe[F, Vector[Int], TreePath] = (dataLines: Stream[F, Vector[Int]]) => {
      val dataLinesHandler: Pipe[F, Vector[Int], TreePath] = src =>
        src
          .fold(ResultAccumulator.empty)(processLine)
          .map(_.paths.minBy(_.cost))

      dataLines.through(dataLinesHandler)
    }


    private def processLine(resultAccumulator: ResultAccumulator, treeLine: Vector[Int]): ResultAccumulator = {
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
  }
}
