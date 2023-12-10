fun main() {

    fun List<Int>.toDifferences(): List<MutableList<Int>> {
        val differences = mutableListOf(this.toMutableList())
        while (!differences.last().all { it == 0 }) differences.add(differences.last().zipWithNext { a, b -> b - a }
            .toMutableList())
        return differences.reversed()
    }

    fun List<MutableList<Int>>.next() =
        this.onEachIndexed { i, _ -> this[i].add(if (i == 0) 0 else this[i].last() + this[i - 1].last()) }
            .last().last()

    fun List<MutableList<Int>>.previous() =
        this.onEachIndexed { i, _ -> this[i].add(0, if (i == 0) 0 else this[i].first() - this[i - 1].first()) }
            .last().first()

    fun part1(input: List<String>) = input.map { it.splitToInts() }.map { it.toDifferences() }.sumOf { it.next() }

    fun part2(input: List<String>) = input.map { it.splitToInts() }.map { it.toDifferences() }.sumOf { it.previous() }

    val testInput = readInput("day09_test")
    part1(testInput).check(114)
    part2(testInput).check(2)

    val input = readInput("day09")
    part1(input).println()
    part2(input).println()
}
