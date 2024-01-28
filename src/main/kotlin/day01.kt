
private fun partA(parenthesis: List<Int>): Int {
    return parenthesis.sumOf { it }
}

private fun partB(parenthesis: List<Int>): Int {
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
    val parenthesis = lines[0].map { if (it == '(') 1 else -1 }

    println("Part A: ${partA(parenthesis)}")
    println("Part B: ${partB(parenthesis)}")
}
