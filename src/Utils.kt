import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("data/$name.txt").readLines()

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * The cleaner shorthand for checking a solution.
 */
fun Any?.check(expected: Any?) = check(this == expected) { "$this does not match expected $expected" }

/**
 * Extension properties for head and tail
 */
val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()

/**
 * Extensions for cartesian product
 */
fun <T, S> Iterable<T>.cartesianProduct(other: Iterable<S>): List<Pair<T, S>> {
    return cartesianProduct(other) { first, second -> first to second }
}

fun <T, S, V> Iterable<T>.cartesianProduct(other: Iterable<S>, transformer: (first: T, second: S) -> V): List<V> {
    return this.flatMap { first -> other.map { second -> transformer.invoke(first, second) } }
}

/**
 * Extensions for integer power
 */
fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()
