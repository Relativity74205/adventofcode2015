
private fun partA(lines: List<String>): Int {
    val parenthesis = lines[0].map { if (it == '(') 1 else -1 }
    return parenthesis.sumOf { it }
}

private fun partB(lines: List<String>): Int {
    val parenthesis = lines[0].map { if (it == '(') 1 else -1 }
    var floor = 0
    for (index in parenthesis.indices) {
        floor += parenthesis[index]
        if (floor == -1) {
            return index + 1
        }
    }
    return -1
}

fun main() {
    val lines = readFile(1, false)

    println("Part A: ${partA(lines)}")
    println("Part B: ${partB(lines)}")
}
