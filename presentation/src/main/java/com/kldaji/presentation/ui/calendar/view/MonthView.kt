package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.kldaji.domain.Client
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class MonthView(context: Context, attrs: AttributeSet? = null) : ViewGroup(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
    }

    // lay out date item views
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val iWidth = (width / CalendarLogic.DAYS_PER_WEEK).toFloat()
        val iHeight = (height / CalendarLogic.WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach { v ->
            val l = (index % CalendarLogic.DAYS_PER_WEEK) * iWidth
            val t = (index / CalendarLogic.DAYS_PER_WEEK) * iHeight
            v.layout(l.toInt(), t.toInt(), (l + iWidth).toInt(), (t + iHeight).toInt())
            index++
        }
    }

    fun addDateItemViews(
        firstDateOfMonth: Date,
        dates: List<Date>,
        meetingClientsInMonth: List<List<Client>>,
        runClientsInMonth: List<List<Client>>,
    ) {
        // deliever clients
        dates.forEachIndexed { index, date ->
            addView(DateItemView(
                context,
                firstDateOfMonth = firstDateOfMonth,
                date = date,
                meetingClients = meetingClientsInMonth[index],
                runClients = runClientsInMonth[index]
            ))
        }
    }
}
