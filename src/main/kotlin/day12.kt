private fun partA(line: String): Int {
    val numbers = mutableListOf<Int>()
    var i = 0
    while (i < line.length) {
        val char = line[i]
        if (char.isDigit()) {
            var numberString = ""
            if (i - 1 >= 0 && line[i - 1] == '-') {
                numberString += "-"
            }
            numberString += char.toString()
            while (i + 1 < line.length && line[i + 1].isDigit()) {
                numberString += line[i + 1]
                i++
            }
            numbers.add(numberString.toInt())
        }
        i++
    }

    return numbers.sum()
}

private fun partB(line: String): Int {
    val numbers = mutableListOf<Int>()
    var lastObjectBeginIndex = -1
    var flagRedSequence = false
    val redSequences = mutableListOf<Pair<Int, Int>>()
    var i = 0
    var countBrackets = 0
    var inArray = false
    while (i < line.length) {
        val char = line[i]
        if (char == '[') {
            inArray = true
        }
        if (char == ']') {
            inArray = false
        }
        if (i < line.length - 2 && line.subSequence(i, i + 3) == "red" && !inArray) {
            flagRedSequence = true
        }
        if (char == '{' && !flagRedSequence) {
            lastObjectBeginIndex = i
            countBrackets = 1
        }
        if (char == '{' && flagRedSequence) {
            countBrackets++
        }
        if (char == '}' && flagRedSequence) {
            countBrackets--
            if (countBrackets == 0) {
                flagRedSequence = false
                redSequences.add(Pair(lastObjectBeginIndex, i))
            }
        }
        i++
    }
    println("Red sequences: $redSequences")
    i = 0
    while (i < line.length) {
        val char = line[i]
        if (char.isDigit() && !redSequences.any { i in it.first..it.second }) {
            var numberString = ""
            if (i - 1 >= 0 && line[i - 1] == '-') {
                numberString += "-"
            }
            numberString += char.toString()
            while (i + 1 < line.length && line[i + 1].isDigit()) {
                numberString += line[i + 1]
                i++
            }
            numbers.add(numberString.toInt())
        }
        i++
    }

    return numbers.sum()
}

fun main() {
    val lines = readFile(12, false)

    println("Part A: ${partA(lines[0])}")
    println("Part B: ${partB(lines[0])}")
//    println("Part B: ${partB("""[1,{"c":"red","b":2},3]""")}")
//    println("Part B: ${partB("""{"foo":1,{"c":"red","b":-22},"bar":-3}""")}")
}


//24683 too low
//45362 too low