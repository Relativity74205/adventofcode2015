private data class Password(var password: String) {
    val forbiddenLetters = "iol"

    fun increment() {
        val lastChar = password.last()
        if (lastChar == 'z') {
            password = password.dropLast(1)
            increment()
            password += 'a'
        } else {
            password = password.dropLast(1)
            password += (lastChar.code + 1).toChar()
        }
    }

    fun hasStraight(): Boolean {
        for (i in 0..<password.length - 2) {
            val first = password[i]
            val second = password[i + 1]
            val third = password[i + 2]
            if (third.code - second.code == 1 && second.code - first.code == 1) {
                return true
            }
        }
        return false
    }

    fun hasTwoPairs(): Boolean {
        var pairs = 0
        var i = 0
        while (i < password.length - 1) {
            if (password[i] == password[i + 1]) {
                pairs++
                i += 2
            } else {
                i++
            }
        }
        return pairs >= 2
    }

    fun isValid(): Boolean {
        if (forbiddenLetters.any { password.contains(it) }) {
            return false
        }

        if (!hasStraight()) {
            return false
        }

        if (!hasTwoPairs()) {
            return false
        }

        return true
    }

    fun findNextValidPassword() {
        do {
            increment()
        } while (!isValid())
    }
}


private fun partA(input: String): String {
    return Password(input).apply { findNextValidPassword() }.password
}

private fun partB(input: String): String {
    return Password(input).apply { findNextValidPassword() }.apply { findNextValidPassword() }.password
}

fun main() {
    val input = "hepxcrrq"
    val inputDebug1 = "abcdefgh"  // abcdffaa
    val inputDebug2 = "ghijklmn"  // ghjaabcc

    println("Part A Debug1: ${partA(inputDebug1)}")
    println("Part A Debug1: ${partA(inputDebug2)}")
    println("Part A: ${partA(input)}")
    println("Part B: ${partB(input)}")
}
