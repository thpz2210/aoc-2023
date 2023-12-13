fun main() {

    class Pattern() {
        private val pattern = mutableListOf<MutableList<Char>>()
        private val transposed = mutableListOf<MutableList<Char>>()

        var horizontal: Int? = null
        var vertical: Int? = null
        var from: Pattern? = null
        var replaced: Triple<Int, Int, Char>? = null

        constructor(row: Int, col: Int, from: Pattern) : this() {
            this.from = from
            from.pattern.forEach { addRow(it.joinToString("")) }
            if (from.pattern[row][col] == '#') {
                pattern[row][col] = '.'
                transposed[col][row] = '.'
                replaced = Triple(row, col, '.')
            } else {
                pattern[row][col] = '#'
                transposed[col][row] = '#'
                replaced = Triple(row, col, '#')
            }
        }

        fun addRow(row: String) {
            pattern.add(row.toCharArray().toMutableList())
            if (transposed.isEmpty()) row.forEach { transposed.add(mutableListOf(it)) }
            else transposed.zip(row.toCharArray().toList()) { a, b -> a.add(b) }
        }

        fun init() {
            horizontal = pattern.findHorizontalReflection()
            vertical = transposed.findHorizontalReflection()
        }

        fun value(): Int {
            var value = 0
            if (from == null || from!!.horizontal != horizontal) value += 100 * horizontal!!
            if (from == null || from!!.vertical != vertical) value += vertical!!
            return value
        }

        fun withSmudge() =
            pattern.indices.cartesianProduct(transposed.indices).map { (row, col) -> Pattern(row, col, this) }
                .onEach { it.init() }.filter { it.horizontal != horizontal || it.vertical != vertical }

        private fun MutableList<MutableList<Char>>.isReflectingIndex(index: Int) =
            indices.all { !(0 < index - it && index + it < size) || this[index - it - 1] == this[index + it] }

        private fun MutableList<MutableList<Char>>.findHorizontalReflection() =
            indices.drop(1).firstOrNull { isReflectingIndex(it) } ?: 0
    }

    fun List<String>.toPatterns() = fold(mutableListOf(Pattern())) { patterns, row ->
        if (row.isNotEmpty()) patterns.last().addRow(row) else patterns.add(Pattern())
        patterns
    }.onEach { it.init() }

    fun part1(input: List<String>) = input.toPatterns().sumOf { it.value() }

    fun part2(input: List<String>): Int {
        var t = input.toPatterns().mapIndexed { index, it ->
            val s = it.withSmudge().filter { p -> p.value() != 0 }
//            if (s.size != 2)
//                throw IllegalStateException()
            if (s.isEmpty()) 0 else s.sumOf { it.value() } / s.size
        } //.filter { it.value() != 0 }
        return t.sum()
    }

    val testInput = readInput("day13_test")
    part1(testInput).check(405)
    part2(testInput).check(400)

    val input = readInput("day13")
    part1(input).println()
    part2(input).println()
}
