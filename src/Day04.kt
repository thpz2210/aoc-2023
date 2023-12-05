fun main() {

    class Card(winningNumbers: List<Int>, actualNumbers: List<Int>) {
        var quantity = 1
        val sizeOfWinningNumbers = winningNumbers.intersect(actualNumbers).size
        val value = if (sizeOfWinningNumbers == 0) 0 else (2.pow(sizeOfWinningNumbers - 1))
    }

    fun String.toCard(): Card {
        val split = this.split(":", "|")
        return Card(split[1].splitToInts(), split[2].splitToInts())
    }

    fun List<String>.toCards() = this.map { it.toCard() }

    fun part1(input: List<String>) = input.toCards().sumOf { it.value }

    fun part2(input: List<String>): Int {
        val cards = input.toCards()
        return cards.onEachIndexed { i, card ->
            cards.subList(i + 1, i + card.sizeOfWinningNumbers + 1).forEach { it.quantity += card.quantity } }
            .sumOf { it.quantity }
    }

    val testInput = readInput("day04_test")
    part1(testInput).check(13)
    part2(testInput).check(30)

    val input = readInput("day04")
    part1(input).println()
    part2(input).println()
}
