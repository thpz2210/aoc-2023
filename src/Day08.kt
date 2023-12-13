fun main() {

    data class Node(val id: String, var left: String, var right: String)

    data class Config(val node: String, val instruction: Int, val index: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Config

            if (node != other.node) return false
            if (instruction != other.instruction) return false

            return true
        }

        override fun hashCode(): Int {
            var result = node.hashCode()
            result = 31 * result + instruction
            return result
        }
    }

    class Network(val instructions: String, val nodes: Map<String, Node>) {

        fun next(index: Int, node: String) = when (instructions[index % instructions.length]) {
            'L' -> nodes[node]!!.left
            else -> nodes[node]!!.right
        }

        fun analyseSpan(node: String): Pair<Int, List<Config>> {
            val seen = mutableSetOf<Config>()
            var newNode = node
            var index = 0
            while (seen.add(Config(newNode, index % instructions.length, index))) {
                newNode = next(index, newNode)
                index++
            }
            return Pair(index - 1, seen.filter { it.node.endsWith("Z") }.map { it })
        }
    }

    fun List<String>.toNetwork() =
        this.drop(2).map { Node(it.substring(0, 3), it.substring(7, 10), it.substring(12, 15)) }.associateBy { it.id }

    fun part1(input: List<String>): Int {
        val network = Network(input.first(), input.toNetwork())
        return generateSequence(Pair(0, "AAA")) {
            Pair(it.first + 1, network.next(it.first, it.second))
        }.takeWhile { it.second != "ZZZ" }.count()
    }

    fun part2(input: List<String>): Int {

        fun findLCM(a: Int, b: Int): Int {
            val larger = if (a > b) a else b
            val maxLcm = a * b
            var lcm = larger
            while (lcm <= maxLcm) {
                if (lcm % a == 0 && lcm % b == 0) {
                    return lcm
                }
                lcm += larger
            }
            return maxLcm
        }

        fun findLCMOfListOfNumbers(numbers: List<Int>): Int {
            var result = numbers[0]
            for (i in 1 until numbers.size) {
                result = findLCM(result, numbers[i])
            }
            return result
        }

        val network = Network(input.first(), input.toNetwork())
        val startNodes = network.nodes.keys.filter { it.endsWith('A') }
        for (startNode in startNodes) {
            println(network.analyseSpan(startNode))
        }


        return findLCMOfListOfNumbers(startNodes.map { network.analyseSpan(it) }.flatMap { it.second.map { l -> l.index } })
    }

    val testInput1 = readInput("day08_test_1")
    part1(testInput1).check(2)

    val testInput2 = readInput("day08_test_2")
    part1(testInput2).check(6)

    val testInput3 = readInput("day08_test_3")
    part2(testInput3).check(6)

    val input = readInput("day08")
    part1(input).println()
    part2(input).println()
}
