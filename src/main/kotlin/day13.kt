private data class Mapping(var map: Map<Pair<String, String>, Int>) {
    fun getHappiness(person1: String, person2: String): Int {
        return map[Pair(person1, person2)] ?: map[Pair(person2, person1)] ?: 0
    }

    private fun getPeople(): Set<String> {
        return map.keys.flatMap { listOf(it.first, it.second) }.toSet()
    }

    fun addPerson(person: String) {
        val peoples = getPeople()
        val newMap = map.toMutableMap()
        for (people in peoples) {
            newMap[Pair(person, people)] = 0
            newMap[Pair(people, person)] = 0
        }
        map = newMap.toMap()
    }

    fun permutations(): List<List<String>> {
        fun _permutations(permutationParts: List<List<String>>, people: Set<String>): List<List<String>> {
            val newPermutations = mutableListOf<List<String>>()
            for (person in people) {
                for (permutation in permutationParts) {
                    if (person !in permutation) {
                        val newPermutation = permutation.toMutableList()
                        newPermutation.add(person)
                        newPermutations.add(newPermutation)
                    }
                }
            }
            return newPermutations
        }

        val peoples = getPeople()
        var permutations = listOf(listOf(peoples.first()))
        for (i in 1..<peoples.size) {
            permutations = _permutations(permutations, peoples)
        }

        return permutations
    }


    fun calculateHappiness(permutation: List<String>): Int {
        var happiness = 0
        for (i in 0..<permutation.size - 1) {
            happiness += getHappiness(permutation[i], permutation[i + 1])
            happiness += getHappiness(permutation[i + 1], permutation[i])
        }
        happiness += getHappiness(permutation[permutation.size - 1], permutation[0])
        happiness += getHappiness(permutation[0], permutation[permutation.size - 1])
        return happiness
    }
}

private fun parseInput(lines: List<String>): Mapping {
    val map = mutableMapOf<Pair<String, String>, Int>()
    for (line in lines) {
        val parts = line.split(" ")
        val key = Pair(parts[0], parts[parts.size - 1].replace(".", ""))
        val value = if (parts[2] == "gain") {
            parts[3].toInt()
        } else {
            -parts[3].toInt()
        }
        map[key] = value
    }
    return Mapping(map.toMap())
}

private fun partA(mapping: Mapping): Int {
    val permutations = mapping.permutations()
    val foo = permutations.map { mapping.calculateHappiness(it) }
    return foo.max()
}

private fun partB(mapping: Mapping): Int {
    mapping.addPerson("Me")
    val permutations = mapping.permutations()
    return permutations.maxOf { mapping.calculateHappiness(it) }
}

fun main() {
    val lines = readFile(13, false)
    val linesDebug = readFile(13, true)
    val mapping = parseInput(lines)
    val mappingDebug = parseInput(linesDebug)

    println("Part A Debug: ${partA(mappingDebug)}")
    println("Part A: ${partA(mapping)}")
    println("Part B Debug: ${partB(mappingDebug)}")
    println("Part B: ${partB(mapping)}")
}
