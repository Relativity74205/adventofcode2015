private data class Reindeer(val name: String, val speed: Int, val flyTime: Int, val restTime: Int) {
    fun distanceAfter(time: Int): Int {
        val cycleTime = flyTime + restTime
        val cycles = time / cycleTime
        val remainingTime = time % cycleTime
        val flyTimeInCycle = listOf(flyTime, remainingTime).min()
        return cycles * flyTime * speed + flyTimeInCycle * speed
    }
}

private fun getReindeer(lines: List<String>): List<Reindeer> {
    return lines.map { line ->
        val parts = line.split(" ")
        val name = parts[0]
        val speed = parts[3].toInt()
        val flyTime = parts[6].toInt()
        val restTime = parts[13].toInt()
        Reindeer(name, speed, flyTime, restTime)
    }
}

private fun partA(reindeer: List<Reindeer>): Int {
    return reindeer.maxOf { it.distanceAfter(2503) }
}

private fun partB(reindeer: List<Reindeer>): Int {
    val scores = mutableMapOf<String, Int>()
    for (i in 1..2503) {
        val distances = reindeer.associate { it.name to it.distanceAfter(i) }
        val maxDistance = distances.maxOf { it.value }
        val leaders = distances.filter { it.value == maxDistance }.keys
        for (leader in leaders) {
            scores[leader] = (scores[leader] ?: 0) + 1
        }
    }
    return scores.maxOf { it.value }
}

fun main() {
    val lines = readFile(14, false)
    val linesDebug = readFile(14, true)

    println("Part A Debug: ${partA(getReindeer(linesDebug))}")
    println("Part A: ${partA(getReindeer(lines))}")
    println("Part B Debug: ${partB(getReindeer(linesDebug))}")
    println("Part B: ${partB(getReindeer(lines))}")
}
