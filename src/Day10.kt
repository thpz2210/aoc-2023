fun main() {

    data class Coordinate(val row: Int, val col: Int)

    class Grid(input: List<String>) {
        private val grid =
            input.flatMapIndexed { row, s -> s.toList().mapIndexed { col, c -> Coordinate(row, col) to c } }.toMap()

        val maxRow = grid.maxOf { it.key.row }
        val maxCol = grid.maxOf { it.key.col }

        val start = grid.filterValues { it == 'S' }.firstNotNullOf { it.key }

        fun north(coordinate: Coordinate) =
            if (coordinate.row - 1 >= 0) Coordinate(coordinate.row - 1, coordinate.col) else null

        fun east(coordinate: Coordinate) =
            if (coordinate.col + 1 <= maxCol) Coordinate(coordinate.row, coordinate.col + 1) else null

        fun south(coordinate: Coordinate) =
            if (coordinate.row + 1 <= maxRow) Coordinate(coordinate.row + 1, coordinate.col) else null

        fun west(coordinate: Coordinate) =
            if (coordinate.col - 1 >= 0) Coordinate(coordinate.row, coordinate.col - 1) else null

        fun connectedTo(coordinate: Coordinate?): List<Coordinate> {
            if (coordinate == null) return listOf()
            return when (grid[coordinate]) {
                '|' -> listOfNotNull(north(coordinate), south(coordinate))
                '-' -> listOfNotNull(east(coordinate), west(coordinate))
                'L' -> listOfNotNull(north(coordinate), east(coordinate))
                'J' -> listOfNotNull(north(coordinate), west(coordinate))
                '7' -> listOfNotNull(south(coordinate), west(coordinate))
                'F' -> listOfNotNull(south(coordinate), east(coordinate))
                '.' -> listOf()
                else -> listOfNotNull(
                    if (connectedTo(north(coordinate)).contains(coordinate)) north(coordinate) else null,
                    if (connectedTo(east(coordinate)).contains(coordinate)) east(coordinate) else null,
                    if (connectedTo(south(coordinate)).contains(coordinate)) south(coordinate) else null,
                    if (connectedTo(west(coordinate)).contains(coordinate)) west(coordinate) else null,
                )
            }
        }
    }

    fun part1(input: List<String>): Any {
        val grid = Grid(input)
        return generateSequence(Pair<Coordinate?, MutableSet<Coordinate>>(grid.start, mutableSetOf())) { (next, path) ->
            path.add(next!!)
            Pair(
                grid.connectedTo(next).firstOrNull() { !path.contains(it) },
                path
            )
        }.takeWhile { (next, _) -> next != null }.count() / 2
    }

    fun part2(input: List<String>) = 0

    val testInput1 = readInput("day10_test_1")
    part1(testInput1).check(4)
    part2(testInput1).check(0)

    val testInput2 = readInput("day10_test_2")
    part1(testInput2).check(8)
    part2(testInput2).check(0)

    val input = readInput("day10")
    part1(input).println()
    part2(input).println()
}
