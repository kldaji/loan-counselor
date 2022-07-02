package com.kldaji.presentation.util

import android.graphics.Color
import com.kldaji.presentation.ui.calendar.entity.Day
import java.util.*

object CalendarLogic {

    const val WEEKS_PER_MONTH = 6
    const val DAYS_PER_WEEK = 7

    fun getDayColor(day: Day): Int {
        return when (day) {
            Day.SUN -> Color.parseColor("#ff0000")
            Day.SAT -> Color.parseColor("#0000ff")
            else -> Color.parseColor("#000000")
        }
    }

    fun getFirstDateOfMonthTimestamp(monthAmount: Int): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.add(Calendar.MONTH, monthAmount)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis
    }

    fun getStartOfTodayTimestamp(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis
    }

    fun getEndOfTodayTimestamp(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.add(Calendar.DATE, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis - 1
    }

    fun getAfterOneMonthTimestamp(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis
    }

    fun getDateList(firstDateOfMonth: Date): List<Date> {
        val dates = mutableListOf<Date>()

        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = firstDateOfMonth
        val prevMonthOffset = getPrevMonthOffset(calendar)
        calendar.add(Calendar.DATE, -prevMonthOffset)
        val total = DAYS_PER_WEEK * WEEKS_PER_MONTH

        for (i in 0 until total) {
            calendar.add(Calendar.DATE, 1)
            dates.add(calendar.time)
        }
        return dates
    }

    private fun getPrevMonthOffset(calendar: Calendar): Int {
        return calendar.get(Calendar.DAY_OF_WEEK) % 7
    }

    fun getDateNumber(date: Date): String {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = date
        return calendar.get(Calendar.DATE).toString()
    }

    fun isSameMonth(firstDateOfMonth: Date, date: Date): Boolean {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = firstDateOfMonth
        val month = calendar.get(Calendar.MONTH)
        calendar.time = date
        return month == calendar.get(Calendar.MONTH)
    }

    fun isFirstDateOfMonth(date: Date): Boolean {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.MILLISECOND) == 0
    }

    fun getMonth(monthAmount: Int): Int {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.add(Calendar.MONTH, monthAmount)
        return calendar.get(Calendar.MONTH) + 1 // month starts with 0
    }
}
