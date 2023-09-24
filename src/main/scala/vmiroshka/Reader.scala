package vmiroshka

import cats.effect.{Async, IO, Sync}
import fs2._
import fs2.io.file.{Files, Path}

trait Reader[F[_]] {
  def readLines: Stream[F, String]
}

object Reader {
  class ConsoleReader[F[_] : Async](chunkSize: Int = 4096) extends Reader[F] {
    override def readLines: Stream[F, String] = {
      fs2.io.stdinUtf8(chunkSize).through(text.lines)
    }
  }

  class FileReader[F[_] : Files](path: Path) extends Reader[F] {
    override def readLines: Stream[F, String] =
      Files[F].readUtf8Lines(path)
  }
}
