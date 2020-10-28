package net.novate.base.time

/**
 * 时间格式化器
 */
fun interface TimeFormatter {
    fun format(time: Long): String
}