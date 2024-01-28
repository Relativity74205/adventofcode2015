private enum class Operation {
    ON, OFF, TOGGLE
}

private data class Instruction(val operation: String, val startX: Int, val startY: Int, val endX: Int, val endY: Int)

private fun partA(instructions: List<Instruction>): Int {
    val grid = Array(1000) { Array(1000) { false } }

    for (instruction in instructions) {
        for (x in instruction.startX..instruction.endX) {
            for (y in instruction.startY..instruction.endY) {
                when (instruction.operation) {
                    "ON" -> grid[x][y] = true
                    "OFF" -> grid[x][y] = false
                    "TOGGLE" -> grid[x][y] = !grid[x][y]
                }
            }
        }
    }

    return grid.sumOf { row -> row.count { it } }
}

private fun partB(instructions: List<Instruction>): Int {
    val grid = Array(1000) { Array(1000) { 0 } }

    for (instruction in instructions) {
        for (x in instruction.startX..instruction.endX) {
            for (y in instruction.startY..instruction.endY) {
                when (instruction.operation) {
                    "ON" -> grid[x][y] += 1
                    "OFF" -> grid[x][y] = maxOf(0, grid[x][y] - 1)
                    "TOGGLE" -> grid[x][y] += 2
                }
            }
        }
    }

    return grid.sumOf { row -> row.sumOf { it } }
}

fun main() {
    val lines = readFile(6, false)
    val instructions = lines.map { line ->
        val operation = when {
            line.startsWith("turn on") -> Operation.ON
            line.startsWith("turn off") -> Operation.OFF
            else -> Operation.TOGGLE
        }
        val instructionParts = line.split(" ")
        val startX = instructionParts[instructionParts.size - 3].split(",")[0].toInt()
        val startY = instructionParts[instructionParts.size - 3].split(",")[1].toInt()
        val endX = instructionParts[instructionParts.size - 1].split(",")[0].toInt()
        val endY = instructionParts[instructionParts.size - 1].split(",")[1].toInt()
        Instruction(operation.name, startX, startY, endX, endY)
    }

    println("Part A: ${partA(instructions)}")
    println("Part B: ${partB(instructions)}")
}
