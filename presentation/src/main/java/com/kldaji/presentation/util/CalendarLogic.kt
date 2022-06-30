package com.kldaji.presentation.util

import android.graphics.Color
import com.kldaji.presentation.ui.calendar.entity.Day

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
}
