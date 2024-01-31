import java.lang.Exception

private data class Wire(val identifier: String, val signalSource: String) {
    private var signalValue: Int? = null

    private fun determineSignalValue(value: String, wires: Map<String, Wire>): Int {
        return if (value.toIntOrNull() != null) {
            value.toInt()
        } else {
            wires[value]!!.processSignalSource(wires)
        }
    }

    fun setSignalValue(value: Int) {
        signalValue = value
    }

    fun reset() {
        signalValue = null
    }

    fun processSignalSource(wires: Map<String, Wire>): Int {
        if (signalValue != null) {
            return signalValue!!
        }

        val signalSourceParts = signalSource.split(" ")
        when (signalSourceParts.size) {
            1 -> {
                signalValue = determineSignalValue(signalSourceParts[0], wires)
            }
            2 -> {
                val signalSource = determineSignalValue(signalSourceParts[1], wires)
                signalValue = Integer.parseInt(Integer.toBinaryString(signalSource).padStart(16, '0').replace('0', '2').replace('1', '0').replace('2', '1'), 2)
            }
            3 -> {
                when (val operation = signalSourceParts[1]) {
                    "AND" -> {
                        val signalSource1Wire = determineSignalValue(signalSourceParts[0], wires)
                        val signalSource2Wire = determineSignalValue(signalSourceParts[2], wires)
                        signalValue = signalSource1Wire.and(signalSource2Wire)
                    }
                    "OR" -> {
                        val signalSource1Wire = determineSignalValue(signalSourceParts[0], wires)
                        val signalSource2Wire = determineSignalValue(signalSourceParts[2], wires)
                        signalValue = signalSource1Wire.or(signalSource2Wire)
                    }
                    "LSHIFT" -> {
                        val signalSource = determineSignalValue(signalSourceParts[0], wires)
                        val amountBits = signalSourceParts[2]
                        signalValue = signalSource.shl(amountBits.toInt())
                    }
                    "RSHIFT" -> {
                        val signalSource = determineSignalValue(signalSourceParts[0], wires)
                        val amountBits = signalSourceParts[2]
                        signalValue = signalSource.shr(amountBits.toInt())
                    }
                    else -> { throw Exception("Unknown operation: $operation") }
                }
            }
            else -> { throw Exception("Unknown signal source: $signalSource") }
        }
        return signalValue!!
    }
}

private fun getWire(line: String): Wire {
    val identifier = line.split(" -> ")[1]
    val signalSource = line.split(" -> ")[0]
    return Wire(identifier, signalSource)
}

private fun partA(wireMap: Map<String, Wire>): Int {
    wireMap.forEach{ it.value.reset() }
    return wireMap["a"]!!.processSignalSource(wireMap)
}

private fun partB(wireMap: Map<String, Wire>): Int {
    wireMap.forEach{ it.value.reset() }
    val override = wireMap["a"]!!.processSignalSource(wireMap)
    wireMap.forEach{ it.value.reset() }
    wireMap["b"]!!.setSignalValue(override)
    return wireMap["a"]!!.processSignalSource(wireMap)
}

fun main() {
    val lines = readFile(7, false)
    val wires = lines.map { getWire(it) }
    val wireMap = wires.associateBy { it.identifier }

    println("Part A: ${partA(wireMap)}")
    println("Part B: ${partB(wireMap)}")
}
