// TODO solve with dynamic programming as this brute force solution is quite inefficient for large number of cities

private fun getDistanceObjects(lines: List<String>): Map<Set<String>, Int> {
    return lines.associate { line ->
        val parts = line.split(" ")
        setOf(parts[0], parts[2]) to parts[4].toInt()
    }
}

private fun getAllPaths(distances: Map<Set<String>, Int>): List<List<String>> {
    val cities = distances.flatMap { it.component1() }.toSet().toList()
    var paths = cities.map { listOf(it) }
    for (i in 1..<cities.size) {
        val newPaths = mutableListOf<List<String>>()
        for (path in paths) {
            for (city in cities) {
                if (city !in path) {
                    val newPath = path.toMutableList()
                    newPath.add(city)
                    newPaths.add(newPath.toList())
                }
            }
        }
        paths = newPaths.toList()
    }
    return paths
}

private fun partA(distances: Map<Set<String>, Int>): Int {
    val paths = getAllPaths(distances)
    return paths.minOf { path ->
        path.windowed(2).sumOf { pair ->
            distances[setOf(pair[0], pair[1])] ?: distances[setOf(pair[1], pair[0])] ?: 0
        }
    }
}

private fun partB(distances: Map<Set<String>, Int>): Int {
    val paths = getAllPaths(distances)
    return paths.maxOf { path ->
        path.windowed(2).sumOf { pair ->
            distances[setOf(pair[0], pair[1])] ?: distances[setOf(pair[1], pair[0])] ?: 0
        }
    }
}

fun main() {
    val lines = readFile(9, false)
    val linesDebug = readFile(9, true)

    println("Part A Debug: ${partA(getDistanceObjects(linesDebug))}")
    println("Part A: ${partA(getDistanceObjects(lines))}")
    println("Part B Debug: ${partB(getDistanceObjects(linesDebug))}")
    println("Part B: ${partB(getDistanceObjects(lines))}")
}
