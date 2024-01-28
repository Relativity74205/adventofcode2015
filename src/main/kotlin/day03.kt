
private data class Coordinate(val x: Int, val y: Int) {
    fun move(direction: Char): Coordinate {
        return when (direction) {
            '^' -> Coordinate(x, y + 1)
            'v' -> Coordinate(x, y - 1)
            '>' -> Coordinate(x + 1, y)
            '<' -> Coordinate(x - 1, y)
            else -> throw Exception("Invalid direction")
        }
    }
}


private fun partA(directions: String): Int {
    var position = Coordinate(0, 0)
    val visited = mutableSetOf(position)
    for (direction in directions) {
        position = position.move(direction)
        visited.add(position)
    }
    return visited.size
}

private fun partB(directions: String): Int {
    var positionSanta = Coordinate(0, 0)
    var positionRobot = Coordinate(0, 0)
    val visitedSanta = mutableSetOf(positionSanta)
    val visitedRobot = mutableSetOf(positionRobot)
    for (index in directions.indices) {
        if (index % 2 == 0) {
            positionSanta = positionSanta.move(directions[index])
            visitedSanta.add(positionSanta)
        } else {
            positionRobot = positionRobot.move(directions[index])
            visitedRobot.add(positionRobot)
        }
    }

    return visitedSanta.union(visitedRobot).size
}

fun main() {
    val lines = readFile(3, false)

    println("Part A: ${partA(lines[0])}")
    println("Part B: ${partB(lines[0])}")
}
