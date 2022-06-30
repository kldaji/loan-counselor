package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import com.kldaji.presentation.ui.calendar.entity.Day
import com.kldaji.presentation.util.CalendarLogic

class DayItemView(context: Context, private val day: Day) : View(context) {

    private val bounds = Rect()
    private var paint: Paint

    init {
        paint = TextPaint().apply {
            isAntiAlias = true
            color = CalendarLogic.getDayColor(day)
            textSize = 50f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        paint.getTextBounds(day.dayOfWeek, 0, day.dayOfWeek.length, bounds)
        canvas.drawText(
            day.dayOfWeek,
            (width / 2 - bounds.width() / 2).toFloat(),
            (height / 2 + bounds.height() / 2).toFloat(),
            paint
        )
    }
}
