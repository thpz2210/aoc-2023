fun main() {

    class Row(val row: MutableList<Char>, val expectedGroups: List<Int>) {

        var possibleArrangements = 0

        fun actualGroups() = row.takeWhile { it != '?' }.fold(mutableListOf(0)) { acc, c ->
            if (c == '#')
                acc[acc.lastIndex] += 1
            else
                acc.add(0)
            acc
        }.filter { it > 0 }

        fun fillArrangements() {
            val pivot = row.indexOfFirst { it == '?' }

            if (pivot == -1) {
                if (actualGroups() == expectedGroups)
                    possibleArrangements += 1
                return
            }

            val actualGroups = actualGroups()

            if (actualGroups.size > expectedGroups.size)
                return

            if (actualGroups.size > 1 && actualGroups.subList(0, actualGroups.size - 1) != expectedGroups.subList(0, actualGroups.size - 1))
                return

            row[pivot] = '#'
            fillArrangements()
            row[pivot] = '.'
            fillArrangements()
            row[pivot] = '?'
        }

        fun possibleArrangements(): Int {
            fillArrangements()
            return possibleArrangements
        }
    }

    fun List<String>.toRows() =
        map { Row(it.split(" ")[0].toMutableList(), it.split(" ")[1].split(",").map { c -> c.toInt() }) }

    fun List<String>.unfold() = map {
        val conditions = it.split(" ")[0]
        val groups = it.split(" ")[1]
        ("$conditions?") + ("$conditions?") + ("$conditions?") + ("$conditions?") + ("$conditions ") +
                ("$groups,") + ("$groups,") + ("$groups,") + ("$groups,") + groups
    }

    fun part1(input: List<String>) = input.toRows().mapIndexed { index, row ->
        println(index)
        row.possibleArrangements()
    }.sum()

    fun part2(input: List<String>) = input.unfold().toRows().mapIndexed { index, row ->
        println(index)
        row.possibleArrangements()
    }.sum()

    val testInput = readInput("day12_test")
    part1(testInput).check(21)
    part2(testInput).check(525152)

    val input = readInput("day12")
    part1(input).println()
    part2(input).println()
}
