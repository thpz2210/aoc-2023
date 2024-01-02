fun main() {

    class Row(val condition: String, val expected: List<Int>) {

        val count: Int
            get() = count(0, listOf(condition))

        private fun String.matches(): Boolean {
            val actual = substringBefore('?').fold(mutableListOf(0)) { acc, c ->
                if (c == '#')
                    acc[acc.lastIndex] += 1
                else if (acc.last() > 0)
                    acc.add(0)
                acc
            }.filter { it > 0 }
            if ('?' !in this)
                return actual == expected
            if (actual.size > expected.size)
                return false
            if (actual.size > 1 && (0..<actual.lastIndex).any { actual[it] != expected[it] })
                return false
            return true
        }

        private tailrec fun count(completeCount: Int, conditions: List<String>): Int {
            val (incomplete, complete) = conditions.partition { '?' in it }
            if (incomplete.isEmpty())
                return completeCount + complete.count { it.matches() }
            return count(
                completeCount + complete.count { it.matches() },
                incomplete.flatMap {
                    listOf(
                        it.substringBefore('?') + '#' + it.substringAfter('?'),
                        it.substringBefore('?') + '.' + it.substringAfter('?')
                    )
                }.filter { it.matches() })
        }
    }

    fun List<String>.toRows() = map { line ->
        val split = line.split(" ", ",")
        Row(split.head, split.tail.map { it.toInt() })
    }

    fun List<Row>.unfold() = map { row ->
        Row(
            (1..5).joinToString("?") { row.condition },
            (1..5).flatMap { row.expected }
        )
    }

    fun part1(input: List<String>) = input.toRows().sumOf { it.count }

    fun part2(input: List<String>) = input.toRows().unfold().sumOf { it.count }

    val testInput = readInput("day12_test")
    part1(testInput).check(21)
    part2(testInput).check(525152)

    val input = readInput("day12")
    part1(input).println()
    //part2(input).println()
}
