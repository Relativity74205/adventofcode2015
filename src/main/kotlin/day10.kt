private fun lookAndSay(input: String): String {
    var result = ""
    var count = 0
    for (i in 0..<input.length-1) {
        count++
        if (input[i] != input[i + 1]) {
            val foo = input[i]
            result += "$count$foo"
            count = 0
        }
    }
    result += count + 1
    result += input[input.length-1]
    return result
}

private fun partA(input: String): Int {
    var result = input
    for (i in 1..40) {
        result = lookAndSay(result)
    }
    return result.length
}

private fun partB(input: String): Int {
    var result = input
    for (i in 1..50) {
        println(i)
        result = lookAndSay(result)
    }
    return result.length
}

fun main() {
    val input = "1113222113"

    println("Part A: ${partA(input)}")
    println("Part B: ${partB(input)}")
}
