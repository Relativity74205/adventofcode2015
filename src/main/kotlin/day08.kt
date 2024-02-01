private data class Result(val numberMemoryChars: Int, val numberEncodedChars: Int) {
    fun difference(): Int {
        return numberEncodedChars - numberMemoryChars
    }
}

private fun parseStringA(line: String): Result {
    var numberMemoryChars = 0
    var numberEncodedChars = 0
    var i = 0
    while (i < line.length) {
        val char = line[i].toString()
        if (char == """"""") {
            i++
            numberEncodedChars++
            continue
        }
        if (char == """\""") {
            if (line[i+1] == 'x') {
                i += 4
                numberEncodedChars += 4
            } else {
                i += 2
                numberEncodedChars += 2
            }
            numberMemoryChars++
            continue
        }
        numberMemoryChars++
        numberEncodedChars++
        i++
    }
    return Result(numberMemoryChars, numberEncodedChars)
}

private fun parseStringB(line: String): Result {
    var parsedLine = """""""
    for (char in line) {
        if (char.toString() == """"""") {
            parsedLine += """\""""
        } else if (char.toString() == """\""") {
            parsedLine += """\\"""
        } else {
            parsedLine += char
        }
    }
    parsedLine += """""""
    return Result(line.length, parsedLine.length)
}

private fun partA(lines: List<String>): Int {
    val results = lines.map { parseStringA(it) }
    return results.sumOf { it.difference() }
}

private fun partB(lines: List<String>): Int {
    val results = lines.map { parseStringB(it) }
    return results.sumOf { it.difference() }
}

fun main() {
    val lines = readFile(8, false)

    println("Part A: ${partA(lines)}")
    println("Part B: ${partB(lines)}")
}
