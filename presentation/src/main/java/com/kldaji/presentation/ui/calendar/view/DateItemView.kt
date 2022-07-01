package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class DateItemView(context: Context, private val firstDateOfMonth: Date, private val date: Date) :
    View(context) {

    private val bounds = Rect()
    private var paint: Paint
    private var dateNumber: String = CalendarLogic.getDateNumber(date)

    init {
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#000000")
            if (!CalendarLogic.isSameMonth(firstDateOfMonth, date)) alpha = 50
            textSize = 30f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        paint.getTextBounds(dateNumber, 0, dateNumber.length, bounds)
        canvas.drawText(
            dateNumber,
            (width / 2 - bounds.width() / 2).toFloat(),
            30f,
            paint
        )
    }
}
