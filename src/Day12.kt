fun main() {

    class Row(val row: String, val groups: List<Int>) {

        val arrangements = mutableSetOf<String>()

        fun fillArrangements(s: String) {
            val pivot = s.indexOfFirst { it == '?' }
            if (pivot == -1) {
                arrangements.add(s)
                return
            } else {
                val groupsSoFar = groups(s)
                if (groupsSoFar.size > groups.size)
                    return
                if (groupsSoFar.size > 1 && groupsSoFar.subList(0, groupsSoFar.size - 1) != groups.subList(
                        0,
                        groupsSoFar.size - 1
                    )
                )
                    return
                fillArrangements(s.substringBefore('?') + '#' + s.substringAfter('?'))
                fillArrangements(s.substringBefore('?') + '.' + s.substringAfter('?'))
            }
        }

        fun groups(s: String): List<Int> {
            val cropped = s.substringBefore('?')
            return cropped.split('.').filter { it.isNotEmpty() }.map { it.length }
        }

        fun possibleArrangements(): Int {
            fillArrangements(row)
            return arrangements.count { groups(it) == groups }
        }
    }

    fun List<String>.toRows() = map { Row(it.split(" ")[0], it.split(" ")[1].split(",").map { c -> c.toInt() }) }

    fun List<String>.unfold() = map {
        val conditions = it.split(" ")[0]
        val groups = it.split(" ")[1]
        ("$conditions?") + ("$conditions?") + ("$conditions?") + ("$conditions?") + ("$conditions ") +
                ("$groups,") + ("$groups,") + ("$groups,") + ("$groups,") + groups
    }

    fun part1(input: List<String>) = input.toRows().sumOf { it.possibleArrangements() }

    fun part2(input: List<String>) = input.unfold().toRows().sumOf { it.possibleArrangements() }

    val testInput = readInput("day12_test")
    part1(testInput).check(21)
    //part2(testInput).check(0)

    val input = readInput("day12")
    part1(input).println()
    // part2(input).println()
}
