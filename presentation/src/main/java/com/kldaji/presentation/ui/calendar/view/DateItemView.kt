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
    showDateDialog: (meetings: List<Client>, runs: List<Client>) -> Unit
) :
    View(context, attrs, defStyleAttr) {

    // date text
    private val bounds = Rect()
    private val dateNumber: String = CalendarLogic.getDateNumber(date)
    private val mMarginTop: Float
    private val dateTextPaint: Paint
    private val mDateTextSize: Float

    // top line
    private val linePaint: Paint

    // circle
    private val circlePaint: Paint
    private val mRadius: Float

    // x 3
    private val numPaint: Paint
    private val mNumTextSize: Float

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.DateItemView,
            defStyleAttr,
            R.style.DateItemViewStyle)

        mMarginTop = typedArray.getDimension(R.styleable.DateItemView_marginTop, 0f)
        mDateTextSize =
            typedArray.getDimensionPixelSize(R.styleable.DateItemView_dateTextSize, 0).toFloat()
        mRadius = typedArray.getDimension(R.styleable.DateItemView_radius, 0f)
        mNumTextSize =
            typedArray.getDimensionPixelSize(R.styleable.DateItemView_numTextSize, 0).toFloat()

        dateTextPaint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#000000")
            if (!CalendarLogic.isSameMonth(firstDateOfMonth, date)) alpha = 50
            textSize = mDateTextSize
        }

        linePaint = Paint().apply {
            color = Color.parseColor("#000000")
            alpha = 50
        }

        circlePaint = Paint()

        numPaint = Paint().apply {
            color = Color.parseColor("#000000")
            textSize = mNumTextSize
        }

        typedArray.recycle()

        this.setOnClickListener {
            showDateDialog(meetingClients, runClients)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        dateTextPaint.getTextBounds(dateNumber, 0, dateNumber.length, bounds)
        canvas.drawText(
            dateNumber,
            (width / 2 - bounds.width() / 2).toFloat(),
            mMarginTop + mDateTextSize,
            dateTextPaint
        )
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, linePaint)

        // meeting
        circlePaint.color = Color.parseColor("#00ff00") // green
        if (meetingClients.isNotEmpty()) {
            canvas.drawCircle(width / 2 - mRadius * 3 / 2,
                height / 2 - mRadius,
                mRadius,
                circlePaint)
            numPaint.getTextBounds("x ${meetingClients.size}", 0, meetingClients.size + 2, bounds)
            canvas.drawText("x ${meetingClients.size}",
                width / 2 + mRadius / 2,
                (height / 2).toFloat(),
                numPaint)
        }

        // run
        circlePaint.color = Color.parseColor("#ffff00") // yellow
        if (runClients.isNotEmpty()) {
            canvas.drawCircle(width / 2 - mRadius * 3 / 2,
                height / 2 + mRadius * 3,
                mRadius,
                circlePaint)
            numPaint.getTextBounds("x ${runClients.size}", 0, runClients.size + 2, bounds)
            canvas.drawText("x ${runClients.size}",
                width / 2 + mRadius / 2,
                height / 2 + mRadius * 4,
                numPaint)
        }
    }
}
