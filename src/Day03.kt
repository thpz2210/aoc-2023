fun main() {

    data class Coordinate(val row: Int, val col: Int)

    class Number(var value: Int, val coordinates: MutableSet<Coordinate>) {
        fun adjacent() = coordinates.flatMap { c -> setOf(
            Coordinate(c.row - 1, c.col - 1),
            Coordinate(c.row - 1, c.col),
            Coordinate(c.row - 1, c.col + 1),
            Coordinate(c.row, c.col - 1),
            Coordinate(c.row, c.col + 1),
            Coordinate(c.row + 1, c.col - 1),
            Coordinate(c.row + 1, c.col),
            Coordinate(c.row + 1, c.col + 1)) }
            .toSet()
    }

    class Symbol(val value: Char, val coordinate: Coordinate)

    fun String.toNumbers(row: Int): Set<Number> {
        val numbers = mutableSetOf<Number>()
        var actualNumber: Number? = null
        for (col in this.indices) {
            if (this[col].isDigit()) {
                if (actualNumber == null) {
                    actualNumber = Number(0, mutableSetOf())
                    numbers.add(actualNumber)
                }
                actualNumber.value *= 10
                actualNumber.value += "${this[col]}".toInt()
                actualNumber.coordinates.add(Coordinate(row, col))
            } else {
                actualNumber = null
            }
        }
        return numbers
    }

    fun List<String>.toNumbers() = this.flatMapIndexed { row, s -> s.toNumbers(row) }

    fun String.toSymbols(row: Int) = this
        .mapIndexed { col, c -> if (c != '.' && !c.isDigit()) Symbol(c, Coordinate(row, col)) else null }
        .filterNotNull()

    fun List<String>.toSymbols() = this.flatMapIndexed { row, s -> s.toSymbols(row) }

    fun part1(input: List<String>): Int {
        val numbers = input.toNumbers()
        val symbols = input.toSymbols().map { it.coordinate }.toSet()
        return numbers.filter { it.adjacent().any { a -> symbols.contains(a) } }.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val numbers = input.toNumbers()
        val symbols = input.toSymbols()
        return symbols.filter { s -> s.value == '*' }
            .map { s -> numbers.filter { n -> n.adjacent().contains(s.coordinate) } }
            .filter { adjacants -> adjacants.size == 2 }
            .sumOf { adjacants -> adjacants[0].value * adjacants[1].value }
    }

    val testInput = readInput("day03_test")
    part1(testInput).check(4361)
    part2(testInput).check(467835)

    val input = readInput("day03")
    part1(input).println()
    part2(input).println()
}
