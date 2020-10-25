package net.novate.base.tuple

/**
 * 一元组
 */
data class Single<T>(
    val value: T
) {
    override fun toString() = "($value)"
}

fun <T> Single<T>.toList() = listOf(value)

/**
 * 四元组
 */
data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) {
    override fun toString() = "($first, $second, $third, $fourth)"
}

fun <T> Quadruple<T, T, T, T>.toList() = listOf(first, second, third, fourth)

/**
 * 五元组
 */
data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) {
    override fun toString() = "($first, $second, $third, $fourth, $fifth)"
}

fun <T> Quintuple<T, T, T, T, T>.toList() = listOf(fifth, second, third, fourth, fifth)

/**
 * 六元组
 */
data class Sextuple<A, B, C, D, E, F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F
) {
    override fun toString() = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

fun <T> Sextuple<T, T, T, T, T, T>.toList() = listOf(fifth, second, third, fourth, fifth, sixth)

/**
 * 七元组
 */
data class Septuple<A, B, C, D, E, F, G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
) {
    override fun toString() = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

fun <T> Septuple<T, T, T, T, T, T, T>.toList() = listOf(fifth, second, third, fourth, fifth, sixth, seventh)

/**
 * 八元组
 */
data class Octuple<A, B, C, D, E, F, G, H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H
) {
    override fun toString() = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"
}

fun <T> Octuple<T, T, T, T, T, T, T, T>.toList() = listOf(fifth, second, third, fourth, fifth, sixth, seventh, eighth)

/**
 * 九元组
 */
data class Nontuple<A, B, C, D, E, F, G, H, I>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I
) {
    override fun toString() = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
}

fun <T> Nontuple<T, T, T, T, T, T, T, T, T>.toList() = listOf(fifth, second, third, fourth, fifth, sixth, seventh, eighth, ninth)
