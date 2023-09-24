package vmiroshka

case class TreePath(cost: Int, nodes: Vector[Int], lastIndexInLevel: Int) {
  def addNode(nodeCost: Int, lastIndexInLevel: Int): TreePath =
    TreePath(cost + nodeCost, nodes.appended(nodeCost), lastIndexInLevel)
}

object TreePath {
  def head(cost: Int, lastIndexInLevel: Int): TreePath =
    TreePath(cost, Vector(cost), lastIndexInLevel)
}