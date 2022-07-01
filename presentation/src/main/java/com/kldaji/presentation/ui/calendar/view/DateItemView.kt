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
    private val textPaint: Paint
    private val linePaint: Paint
    private val dateNumber: String = CalendarLogic.getDateNumber(date)

    init {
        textPaint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#000000")
            if (!CalendarLogic.isSameMonth(firstDateOfMonth, date)) alpha = 50
            textSize = 30f
        }
        linePaint = Paint().apply {
            color = Color.parseColor("#000000")
            alpha = 50
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        textPaint.getTextBounds(dateNumber, 0, dateNumber.length, bounds)
        canvas.drawText(
            dateNumber,
            (width / 2 - bounds.width() / 2).toFloat(),
            30f,
            textPaint
        )
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, linePaint)
    }
}
