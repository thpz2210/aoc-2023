fun main() {

    fun List<String>.findStart() = this.indices.cartesianProduct(this.first().indices)
        .first { (row, col) -> this[row][col] == 'S' }

    fun part1(input: List<String>) = input.findStart()

    fun part2(input: List<String>) = 0

    val testInput1 = readInput("day10_test_1")
    //part1(testInput1).check(0)
    part2(testInput1).check(0)

    val testInput2 = readInput("day10_test_2")
    part1(testInput2).check(0)
    part2(testInput2).check(0)

    val input = readInput("day10")
    part1(input).println()
    part2(input).println()
}
