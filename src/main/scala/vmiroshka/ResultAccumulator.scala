package vmiroshka

case class ResultAccumulator(paths: Vector[TreePath])

object ResultAccumulator {
  def empty: ResultAccumulator = ResultAccumulator(Vector.empty)
}
