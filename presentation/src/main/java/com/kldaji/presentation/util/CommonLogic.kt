package com.kldaji.presentation.util

import java.util.*

object CommonLogic {

    fun getStartOfTodayTimeStamp(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis
    }

    fun getEndOfTodayTimeStamp(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.add(Calendar.DATE, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis - 1
    }

    fun getAfterOneMonthTimeStamp(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.add(Calendar.MONTH, 1)
        return calendar.timeInMillis
    }
}
