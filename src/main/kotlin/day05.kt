private fun niceStringARule1(string: String): Boolean {
    var vowels = 0
    for (element in string) {
        if (element in setOf('a', 'e', 'i', 'o', 'u')) {
            vowels++
        }
    }
    return vowels >= 3
}

private fun niceStringARule2(string: String): Boolean {
    for (element in listOf("ab", "cd", "pq", "xy")) {
        if (string.contains(element)) {
            return false
        }
    }
    return true
}

private fun niceStringARule3(string: String): Boolean {
    for (index in 0..<string.length - 1) {
        if (string[index] == string[index + 1]) {
            return true
        }
    }
    return false
}

private fun niceStringA(string: String): Boolean {
    return !(!niceStringARule1(string) || !niceStringARule2(string) || !niceStringARule3(string))
}

private fun niceStringBRule1(string: String): Boolean {
    for (index in 0..<string.length - 2) {
        val pairString = string.substring(index, index + 2)
        if (string.substring(index + 2).contains(pairString)) {
            return true
        }
    }
    return false
}

private fun niceStringBRule2(string: String): Boolean {
    for (index in 0..<string.length - 2) {
        if (string[index] == string[index + 2]) {
            return true
        }
    }
    return false
}

private fun niceStringB(string: String): Boolean {
    return !(!niceStringBRule1(string) || !niceStringBRule2(string))
}

private fun partA(lines: List<String>): Int {
    return lines.filter{niceStringA(it)}.size
}

private fun partB(lines: List<String>): Int {
    return lines.filter{niceStringB(it)}.size
}

fun main() {
    val lines = readFile(5, false)

    println("Part A: ${partA(lines)}")
    println("Part B: ${partB(lines)}")
}
