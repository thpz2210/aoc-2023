fun main() {

    class Round(val red: Int, val green: Int, val blue: Int)

    class Game(val id: Int, val rounds: List<Round>)

    fun String.cubesOf(color: String) = this.split(",")
        .filter { it.contains(color) }
        .map { it.removeSuffix(color).trim().toInt() }
        .firstOrNull() ?: 0

    fun String.toRound() = Round(this.cubesOf("red"), this.cubesOf("green"), this.cubesOf("blue"))

    fun String.toGame(): Game {
        val split = this.split(":", ";")
        val id = split.head.removePrefix("Game").trim().toInt()
        val rounds = split.tail.map { it.toRound() }
        return Game(id, rounds)
    }

    fun List<String>.toGames() = this.map { it.toGame() }

    fun part1(input: List<String>) = input.toGames()
        .filter { g -> g.rounds.all { r -> r.red <= 12 && r.green <= 13 && r.blue <= 14 } }
        .sumOf { it.id }

    fun part2(input: List<String>) = input.toGames()
        .sumOf { g -> g.rounds.maxOf { it.red } * g.rounds.maxOf { it.green } * g.rounds.maxOf { it.blue } }

    val testInput = readInput("day02_test")
    part1(testInput).check(8)
    part2(testInput).check(2286)

    val input = readInput("day02")
    part1(input).println()
    part2(input).println()
}
