import java.math.BigInteger
import java.security.MessageDigest

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun findHash(key: String, prefix: String): Int {
    var decimal = 0
    while (true){
        if (md5(key + decimal).startsWith(prefix)){
            return decimal
        }

        decimal++
    }
}


private fun partA(key: String): Int {
    return findHash(key, "00000")
}

private fun partB(key: String): Int {
    return findHash(key, "000000")
}

fun main() {
    val lines = readFile(4, false)
    val key = lines[0]

    println("Part A: ${partA(key)}")
    println("Part B: ${partB(key)}")
}
