import kotlin.math.max
import kotlin.math.min

fun main() {

    data class Interval(var left: Long, val right: Long) {

        fun isNotEmpty() = left < right
    }

    class PartialMapping(val dstStart: Long, val srcStart: Long, val size: Long) {

        fun apply(value: Long) = dstStart + (value - srcStart)
    }

    class Mapping(val partials: MutableSet<PartialMapping> = mutableSetOf()) {

        fun apply(intervals: Set<Interval>) = intervals.flatMap { apply(it) }.toSet()

        fun apply(interval: Interval): Set<Interval> {
            val newIntervals =
                partials.sortedBy { it.srcStart }.fold(mutableSetOf<Interval>()) { newIntervals, partial ->
                    val left = Interval(interval.left, min(partial.srcStart, interval.right))
                    if (left.isNotEmpty()) {
                        newIntervals.add(left)
                        interval.left = left.right
                    }
                    val middle = Interval(
                        max(interval.left, partial.srcStart), min(partial.srcStart + partial.size, interval.right)
                    )
                    if (middle.isNotEmpty()) {
                        newIntervals.add(Interval(partial.apply(middle.left), partial.apply(middle.right)))
                        interval.left = middle.right
                    }
                    newIntervals
                }
            if (interval.isNotEmpty())
                newIntervals.add(interval)
            return newIntervals
        }
    }

    fun MutableList<Mapping>.add(line: String): MutableList<Mapping> {
        if (line.endsWith("map:")) {
            this.add(Mapping())
        } else if (line.isNotEmpty()) {
            val split = line.splitToLongs()
            this.last().partials.add(PartialMapping(split[0], split[1], split[2]))
        }
        return this
    }

    fun List<String>.toMappings() =
        this.tail.fold(mutableListOf<Mapping>()) { mappings, line -> mappings.add(line) }.toList()

    fun List<String>.toIntervals(chunkSize: Int = 1) = this.head.splitToLongs(drop = 1).chunked(chunkSize)
        .map { chunk -> Interval(chunk[0], chunk[0] + (chunk.getOrNull(1) ?: 1)) }.toSet()

    fun Set<Interval>.lowestLocation(mappings: List<Mapping>) = this.flatMap { interval ->
        mappings.fold(setOf(interval)) { intervals, mapping -> mapping.apply(intervals) }
    }.minOf { it.left }

    fun part1(input: List<String>) = input.toIntervals().lowestLocation(input.toMappings())

    fun part2(input: List<String>) = input.toIntervals(chunkSize = 2).lowestLocation(input.toMappings())

    val testInput = readInput("day05_test")
    part1(testInput).check(35L)
    part2(testInput).check(46L)

    val input = readInput("day05")
    part1(input).println()
    part2(input).println()
}
