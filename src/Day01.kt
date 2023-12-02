fun main() {
    fun part1(input: List<String>): Int {
        fun String.firstDigit() = this.find { it.isDigit() }
        fun String.lastDigit() = this.findLast { it.isDigit() }
        return input.sumOf { "${it.firstDigit()}${it.lastDigit()}".toInt() }
    }

    fun part2(input: List<String>): Int {
        val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "1", "2", "3", "4", "5", "6", "7", "8", "9")
        fun Pair<Int, String>?.toInt() = digits.indexOf(this!!.second) % 9 + 1
        fun String.firstDigit() = this.findAnyOf(digits).toInt()
        fun String.lastDigit() = this.findLastAnyOf(digits).toInt()
        return input.sumOf { 10 * it.firstDigit() + it.lastDigit() }
    }

    val testInput1 = readInput("day01_test_1")
    part1(testInput1).check(142)

    val testInput2 = readInput("day01_test_2")
    part2(testInput2).check(281)

    val input = readInput("day01")
    part1(input).println()
    part2(input).println()
}
