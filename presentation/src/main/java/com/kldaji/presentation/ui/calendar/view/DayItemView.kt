package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.calendar.entity.Day
import com.kldaji.presentation.util.CalendarLogic

class DayItemView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.dayItemViewStyle,
    private val day: Day,
) : View(context, attrs, defStyleAttr) {

    private val bounds = Rect()
    private var paint: Paint

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.DayItemView,
            defStyleAttr,
            R.style.DayItemViewStyle)
        paint = TextPaint().apply {
            isAntiAlias = true
            color = CalendarLogic.getDayColor(day)
            textSize =
                typedArray.getDimensionPixelSize(R.styleable.DayItemView_textSize, 0).toFloat()
        }
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        paint.getTextBounds(day.dayOfWeek, 0, day.dayOfWeek.length, bounds)
        // set baseline same
        canvas.drawText(
            day.dayOfWeek,
            -bounds.left.toFloat() + (width / 2 - bounds.width() / 2).toFloat(),
            -bounds.top.toFloat(),
            paint
        )
    }
}
