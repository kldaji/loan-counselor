package com.kldaji.presentation.util

import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun longToString(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.timeInMillis = timestamp
        return DateFormat.format("yyyy-MM-dd", calendar).toString()
    }

    fun stringToLong(dateString: String): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = formatter.parse(dateString)
        } catch (e: ParseException) {
            return 0L
        }
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.timeInMillis
    }
}
