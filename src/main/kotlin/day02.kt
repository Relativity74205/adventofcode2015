private data class Package(val l: Int, val w: Int, val h: Int) {
    fun surfaceArea(): Int {
        return 2 * l * w + 2 * w * h + 2 * h * l
    }

    fun smallestSideArea(): Int {
        return listOf(l * w, w * h, h * l).min()
    }

    fun paperNeeded(): Int {
        return surfaceArea() + smallestSideArea()
    }

    fun ribbonLength(): Int {
        return listOf(l + w, w + h, h + l).min() * 2 + l * w * h
    }
}


private fun partA(packages: List<Package>): Int {
    return packages.sumOf { it.paperNeeded() }
}

private fun partB(packages: List<Package>): Int {
    return packages.sumOf { it.ribbonLength() }
}

fun main() {
    val lines = readFile(2, false)
    val packages = lines.map { line ->
        val dimensions = line.split("x").map { it.toInt() }
        Package(dimensions[0], dimensions[1], dimensions[2])
    }

    println("Part A: ${partA(packages)}")
    println("Part B: ${partB(packages)}")
}
