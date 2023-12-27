import kotlin.math.abs

fun main() {

    data class Galaxy(val row: Int, val col: Int) {
        var expandedRow = row.toLong()
        var expandedCol = col.toLong()
        override fun toString(): String {
            return "Galaxy(row=$row, col=$col, expandedRow=$expandedRow, expandedCol=$expandedCol)"
        }
    }

    fun List<String>.toGalaxies() =
        flatMapIndexed { row, s -> s.mapIndexed { col, c -> if (c == '#') Galaxy(row, col) else null }.filterNotNull() }

    fun List<Galaxy>.expandRows(expandBy: Long): List<Galaxy> {
        val rowIndices = map { it.row }
        (0..<rowIndices.max()).forEach { row ->
            if (row !in rowIndices) {
                filter { it.row > row }.forEach { it.expandedRow += expandBy }
            }
        }
        return this
    }

    fun List<Galaxy>.expandCols(expandBy: Long): List<Galaxy> {
        val colIndices = map { it.col }
        (0..<colIndices.max()).forEach { col ->
            if (col !in colIndices) {
                filter { it.col > col }.forEach { it.expandedCol += expandBy }
            }
        }
        return this
    }

    fun List<Galaxy>.expand(expandBy: Long) = expandRows(expandBy).expandCols(expandBy)

    fun solve(input: List<String>, expandBy: Long): Any {
        val galaxies = input.toGalaxies().expand(expandBy)
        return galaxies.mapIndexed { index, galaxy ->
            galaxies.drop(index).sumOf { abs(it.expandedRow - galaxy.expandedRow) + abs(it.expandedCol - galaxy.expandedCol) }
        }.sum()
    }

    fun part1(input: List<String>) = solve(input, 1)

    fun part2(input: List<String>) = solve(input, 1000000L - 1L)

    val testInput = readInput("day11_test")
    part1(testInput).check(374L)
    solve(testInput, 10 - 1).check(1030L)
    solve(testInput, 100 - 1).check(8410L)

    val input = readInput("day11")
    part1(input).println()
    part2(input).println()
}
