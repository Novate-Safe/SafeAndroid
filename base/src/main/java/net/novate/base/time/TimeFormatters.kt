package net.novate.base.time

import android.content.Context
import net.novate.base.R
import java.text.SimpleDateFormat
import java.util.*

// 时间格式化器的一些实现

/**
 * 抽象时间格式化器，提供一些对于时间的操作
 */
abstract class AbstractTimeFormatter : TimeFormatter {
    private val calendar by lazy { Calendar.getInstance() }

    /**
     * 指定时间 [Long] 是否是今天
     */
    protected fun Long.isToday(): Boolean {

        val currentYear: Int
        val currentDayOfYear: Int
        with(calendar.apply { timeInMillis = System.currentTimeMillis() }) {
            currentYear = get(Calendar.YEAR)
            currentDayOfYear = get(Calendar.DAY_OF_YEAR)
        }

        val timeYear: Int
        val timeDayOfYear: Int
        with(calendar.apply { timeInMillis = this@isToday }) {
            timeYear = get(Calendar.YEAR)
            timeDayOfYear = get(Calendar.DAY_OF_YEAR)
        }

        return currentYear == timeYear && currentDayOfYear == timeDayOfYear
    }
}

/**
 * 时间格式化器1
 *
 * 当天:      HH:mm
 * 其他:      yyyy-MM-dd HH:mm
 */
class TimeFormatter1(val context: Context) : AbstractTimeFormatter() {

    private val todayDateFormat by lazy {
        SimpleDateFormat(
            context.getString(R.string.time_format_hh_mm),
            Locale.getDefault()
        )
    }
    private val otherDateFormat by lazy {
        SimpleDateFormat(
            context.getString(R.string.time_format_yyyy_mm_dd_hh_mm),
            Locale.getDefault()
        )
    }

    override fun format(time: Long): String {
        return if (time.isToday()) {
            todayDateFormat.format(time)
        } else {
            otherDateFormat.format(time)
        }
    }
}

/**
 * 时间格式化器2
 *
 * 当天:      HH:mm
 * 其他:      yyyy-MM-dd
 */
class TimeFormatter2(val context: Context) : AbstractTimeFormatter() {

    private val todayDateFormat by lazy {
        SimpleDateFormat(
            context.getString(R.string.time_format_hh_mm),
            Locale.getDefault()
        )
    }
    private val otherDateFormat by lazy {
        SimpleDateFormat(
            context.getString(R.string.time_format_yyyy_mm_dd),
            Locale.getDefault()
        )
    }

    override fun format(time: Long): String {
        return if (time.isToday()) {
            todayDateFormat.format(time)
        } else {
            otherDateFormat.format(time)
        }
    }
}