package vmiroshka

import fs2.Pipe

trait Parser[F[_]] {
  def parseDataLinesPipe(): Pipe[F, String, Vector[Int]]
}

object Parser {
  class SpaceSplitParser[F[_]] extends Parser[F] {
    override def parseDataLinesPipe(): Pipe[F, String, Vector[Int]] =
      stream => stream.map(_.split(" ").map(_.toInt).toVector)
  }
}
