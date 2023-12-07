fun main() {

    data class Hand(var cards: String, val bid: Int, var type: Int = 0, var strength: String = "")

    fun List<String>.toHands() = this.map { it.split(' ') }.map { Hand(it[0], it[1].toInt()) }

    fun calcType(cards: String) = cards.groupingBy { it }.eachCount().values.map {
        when (it) {
            5 -> 6
            4 -> 5
            3 -> 3
            2 -> 1
            else -> 0
        }
    }.sum()

    fun calcStrength(cards: String, orderedCards: List<Char>) =
        cards.map { 'a' + orderedCards.indexOf(it) }.joinToString("")

    fun List<Hand>.withTypeAndStrengthPart1(): List<Hand> {
        val orderedCards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
        return this.onEach { hand ->
            hand.type = calcType(hand.cards)
            hand.strength = calcStrength(hand.cards, orderedCards)
        }
    }

    fun List<Hand>.withTypeAndStrengthPart2(): List<Hand> {
        val orderedCards = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()
        return this.onEach { hand ->
            hand.type = orderedCards.drop(1).maxOf {
                calcType(hand.cards.replace('J', it))
            }
            hand.strength = calcStrength(hand.cards, orderedCards)
        }
    }

    fun part1(input: List<String>) =
        input.toHands().withTypeAndStrengthPart1().sortedWith(compareBy<Hand> { it.type }.thenBy { it.strength })
            .mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()

    fun part2(input: List<String>) =
        input.toHands().withTypeAndStrengthPart2().sortedWith(compareBy<Hand> { it.type }.thenBy { it.strength })
            .mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()

    val testInput = readInput("day07_test")
    part1(testInput).check(6440)
    part2(testInput).check(5905)

    val input = readInput("day07")
    part1(input).println()
    part2(input).println()
}
