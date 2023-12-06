fun main() {

    class Race(val time: Long, val distance: Long) {
        fun distance(millis: Long) = millis * (time - millis)
        fun waysToWin() = (0..time).count { distance < distance(it) }
    }

    fun List<String>.toRaces() = this[0].splitToLongs(" ", ":", drop = 1)
        .zip(this[1].splitToLongs(" ", ":", drop = 1)).map { Race(it.first, it.second) }

    fun part1(input: List<String>) = input.toRaces().productOf { it.waysToWin() }

    fun part2(input: List<String>) = part1(input.map { it.replace(" ", "") })

    val testInput = readInput("day06_test")
    part1(testInput).check(288)
    part2(testInput).check(71503)

    val input = readInput("day06")
    part1(input).println()
    part2(input).println()
}
