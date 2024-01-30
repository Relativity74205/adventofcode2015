
interface BitWiseOperation {
    val operation: String
    fun apply(): Int
}


private data class Wire(val identifier: String, val signalSource: String) {

    private fun getFoo(value: String, wires: Map<String, Wire>): Int {
        return if (value.toIntOrNull() != null) {
            value.toInt()
        } else {
            wires[value]!!.getSignal(wires)
        }
    }

    fun getSignal(wires: Map<String, Wire>): Int {
        val signalSourceParts = signalSource.split(" ")
        when (signalSourceParts.size) {
            1 -> {
                return getFoo(signalSourceParts[0], wires)
            }
            2 -> {
                val operation = signalSourceParts[0]
                val signalSource = signalSourceParts[1]
                return when (operation) {
                    "NOT" -> {
                        val signalSourceWire = wires[signalSource]!!
                        Integer.parseInt(Integer.toBinaryString(signalSourceWire.getSignal(wires)).padStart(16, '0').replace('0', '2').replace('1', '0').replace('2', '1'), 2)
                    }
                    else -> {
                        -1
                    }
                }
            }
            3 -> {
                val operation = signalSourceParts[1]
                when (operation) {
                    "AND" -> {
                        val signalSource1Wire = getFoo(signalSourceParts[0], wires)
                        val signalSource2Wire = getFoo(signalSourceParts[2], wires)
                        return signalSource1Wire.and(signalSource2Wire)
                    }
                    "OR" -> {
                        val signalSource1Wire = getFoo(signalSourceParts[0], wires)
                        val signalSource2Wire = getFoo(signalSourceParts[2], wires)
                        return signalSource1Wire.or(signalSource2Wire)
                    }
                    "LSHIFT" -> {
                        val signalSource = getFoo(signalSourceParts[0], wires)
                        val amountBits = signalSourceParts[2]
                        return signalSource.shl(amountBits.toInt())
                    }
                    "RSHIFT" -> {
                        val signalSource = getFoo(signalSourceParts[0], wires)
                        val amountBits = signalSourceParts[2]
                        return signalSource.shr(amountBits.toInt())
                    }
                    else -> {
                        return -1
                    }
                }
            }
            else -> { return -1 }
        }
    }
}

private fun getWire(line: String): Wire {
    val identifier = line.split(" -> ")[1]
    val signalSource = line.split(" -> ")[0]
    return Wire(identifier, signalSource)
}

private fun partA(lines: List<String>): Int {
    val wires = lines.map { getWire(it) }
    val wireMap = mutableMapOf<String, Wire>()
    for (wire in wires) {
        wireMap[wire.identifier] = wire
    }
    return wireMap["a"]!!.getSignal(wireMap.toMap())
}

private fun partB(lines: List<String>): Int {
    return 0
}

fun main() {
    val lines = readFile(7, false)
    val linesDebug = readFile(7, true)

//    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
//    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}
