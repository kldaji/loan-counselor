package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class DateItemView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.dateItemViewStyle,
    private val firstDateOfMonth: Date,
    private val date: Date,
    private val meetingClients: List<Client>,
    private val runClients: List<Client>,
) :
    View(context, attrs, defStyleAttr) {

    // date text
    private val bounds = Rect()
    private val dateNumber: String = CalendarLogic.getDateNumber(date)
    private val mMarginTop: Float
    private val textPaint: Paint
    private val mTextSize: Float

    // top line
    private val linePaint: Paint

    // circle
    private val circlePaint: Paint
    private val mRadius: Float

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.DateItemView,
            defStyleAttr,
            R.style.DateItemViewStyle)

        mMarginTop = typedArray.getDimension(R.styleable.DateItemView_marginTop, 0f)
        mTextSize =
            typedArray.getDimensionPixelSize(R.styleable.DateItemView_dateTextSize, 0).toFloat()
        mRadius = typedArray.getDimension(R.styleable.DateItemView_radius, 0f)

        textPaint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#000000")
            if (!CalendarLogic.isSameMonth(firstDateOfMonth, date)) alpha = 50
            textSize = mTextSize
        }

        linePaint = Paint().apply {
            color = Color.parseColor("#000000")
            alpha = 50
        }

        circlePaint = Paint()

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        textPaint.getTextBounds(dateNumber, 0, dateNumber.length, bounds)
        canvas.drawText(
            dateNumber,
            (width / 2 - bounds.width() / 2).toFloat(),
            mMarginTop + mTextSize,
            textPaint
        )
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, linePaint)

        // meeting
        circlePaint.color = Color.parseColor("#00ff00") // green
        if (meetingClients.isNotEmpty()) canvas.drawCircle(width / 2 - mRadius / 2,
            height / 2 - mRadius,
            mRadius,
            circlePaint)

        // run
        circlePaint.color = Color.parseColor("#ffff00") // yellow
        if (runClients.isNotEmpty()) canvas.drawCircle(width / 2 - mRadius / 2,
            height / 2 + mRadius * 3,
            mRadius,
            circlePaint)

        // draw text
    }
}
