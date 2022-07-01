package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import java.util.*

class DateItemView(context: Context, private val firstDateOfMonth: Date, private val date: Date) :
    View(context) {

    private val bounds = Rect()
    private var paint: Paint

    init {
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#000000")
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

    }
}
