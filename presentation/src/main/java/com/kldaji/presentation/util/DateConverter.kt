package com.kldaji.presentation.util

import android.content.Context
import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun longToString(context: Context, millis: Long): String {
        val date = Date(millis)
        val dateFormat = DateFormat.getDateFormat(context)
        return dateFormat.format(date)
    }

    fun stringToLong(context: Context, dateString: String): Long {
        val formatter = SimpleDateFormat("m/d/yy")
        var date: Date? = null
        try {
            date = formatter.parse(dateString)
        } catch (e: ParseException) {
            return 0L
        }
        val dateFormat = DateFormat.getDateFormat(context)
        val localeDateString = dateFormat.format(date!!)
        date = formatter.parse(localeDateString)
        return date.time
    }
}
