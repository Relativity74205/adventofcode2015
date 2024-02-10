interface JsonElement {
    fun sum(isRed: Boolean): Int
}

class JsonArray : JsonElement {
    private val elements = mutableListOf<JsonElement>()

    override fun sum(isRed: Boolean): Int {
        return elements.sumOf { it.sum(isRed) }
    }

    fun addElement(element: JsonElement) {
        elements.add(element)
    }
}

class JsonObject : JsonElement {
    private val elements = mutableMapOf<String, JsonElement>()

    override fun sum(isRed: Boolean): Int {
        if (isRed && elements.values.any { it is JsonString && it.isRed() }) {
            return 0
        }
        return elements.values.sumOf { it.sum(isRed) }
    }

    fun addElement(key: String, element: JsonElement) {
        elements[key] = element
    }
}

class JsonString(private val value: String) : JsonElement {
    override fun sum(isRed: Boolean): Int {
        return 0
    }

    fun isRed(): Boolean {
        return value == "red"
    }
}

class JsonNumber(private val value: Int) : JsonElement {
    override fun sum(isRed: Boolean): Int {
        return value
    }
}


private fun findElements(s: String): List<String> {
    val commaPositions = mutableListOf<Int>()
    var i = 0
    var curlyBrackets = 0
    var squareBrackets = 0
    while (i < s.length) {
        val char = s[i]
        when (char) {
            '{' -> curlyBrackets++
            '}' -> curlyBrackets--
            '[' -> squareBrackets++
            ']' -> squareBrackets--
            ',' -> {
                if (curlyBrackets == 0 && squareBrackets == 0) {
                    commaPositions.add(i)
                }
            }
        }
        i++
    }

    if (commaPositions.isEmpty()) {
        return listOf(s)
    }

    val elements = mutableListOf(s.substring(0, commaPositions[0]))
    for (commaPositionIndex in commaPositions.indices) {
        val endOfSubstring = if (commaPositionIndex == commaPositions.indices.last()) {
            s.length
        } else {
            commaPositions[commaPositionIndex + 1]
        }
        elements.add(s.substring(commaPositions[commaPositionIndex] + 1, endOfSubstring))
    }

    return elements.toList()
}


private fun parseJsonString(line: String): JsonElement {
    when (line[0]) {
        '{' -> {
            val jsonObject = JsonObject()
            val elements = findElements(line.substring(1, line.length - 1))
            for (element in elements) {
                val key = element.substring(1, element.indexOf(":") - 1)
                val value = element.substring(element.indexOf(":") + 1, element.length)
                jsonObject.addElement(key, parseJsonString(value))
            }
            return jsonObject
        }
        '[' -> {
            val jsonArray = JsonArray()
            val elements = findElements(line.substring(1, line.length - 1))
            for (element in elements) {
                jsonArray.addElement(parseJsonString(element))
            }
            return jsonArray
        }
        else -> {
            if (line.toIntOrNull() != null) {
                return JsonNumber(line.toInt())
            }
            return JsonString(line.substring(1, line.length - 1))
        }
    }
}

private fun partA(line: String): Int {
    return parseJsonString(line).sum(false)
}

private fun partB(line: String): Int {
    return parseJsonString(line).sum(true)
}

fun main() {
    val lines = readFile(12, false)

    println("Part A: ${partA(lines[0])}")
    println("Part B: ${partB(lines[0])}")
}