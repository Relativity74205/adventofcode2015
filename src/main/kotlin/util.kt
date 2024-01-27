import java.io.File
import java.io.InputStream

fun readFile(day: Int, debug: Boolean, suffix: String = ""): List<String> {
    val path = if (debug) "input_files/debug" else "input_files"
    val dayString = day.toString().padStart(2, '0')
    val inputStream: InputStream = File("$path/day$dayString$suffix.csv").inputStream()
    val lines = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lines.add(it) }

    return lines.toList()
}