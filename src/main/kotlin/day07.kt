import java.lang.Exception

// TODO create cache for a wire, if value is known, return it, otherwise calculate it
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

    fun processSignalSource(wires: Map<String, Wire>): Int {
        val signalSourceParts = signalSource.split(" ")
        if (signalValue != null) {
            return signalValue!!
        }
        when (signalSourceParts.size) {
            1 -> {
                signalValue = determineSignalValue(signalSourceParts[0], wires)
            }
            2 -> {
                val signalSource = signalSourceParts[1]
                val signalSourceValue = wires[signalSource]!!.processSignalSource(wires)
                signalValue = Integer.parseInt(Integer.toBinaryString(signalSourceValue).padStart(16, '0').replace('0', '2').replace('1', '0').replace('2', '1'), 2)
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
    val wireMapCopy = wireMap.map { it.key to it.value.copy() }.toMap()
    return wireMapCopy["a"]!!.processSignalSource(wireMapCopy)
}

private fun partB(wireMap: Map<String, Wire>): Int {
    val wireMapCopy = wireMap.map { it.key to it.value.copy() }.toMap()
    val override = wireMapCopy["a"]!!.processSignalSource(wireMapCopy)
    val wireMapCopy2 = wireMap.map { it.key to it.value.copy() }.toMap().toMutableMap()
    wireMapCopy2["b"] = Wire("b", override.toString())
    return wireMapCopy2["a"]!!.processSignalSource(wireMapCopy2.toMap())
}

fun main() {
    val lines = readFile(7, false)
    val wires = lines.map { getWire(it) }
    val wireMap = wires.associateBy { it.identifier }

    println("Part A: ${partA(wireMap)}")
    println("Part B: ${partB(wireMap)}")
}
