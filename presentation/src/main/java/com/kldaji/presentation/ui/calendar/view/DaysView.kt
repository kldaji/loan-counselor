package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.calendar.entity.Day
import com.kldaji.presentation.util.CalendarLogic

class DaysView(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private var mHeight = 0f

    init {
        Day.values().forEach {
            addView(DayItemView(context, day = it))
        }
        context.theme.obtainStyledAttributes(attrs, R.styleable.DaysView, 0, 0).apply {
            try {
                mHeight = getDimension(R.styleable.DaysView_height, 0f)
            } finally {
                recycle() // shared resource
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // width: match_parent
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            mHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val iWidth = (width / CalendarLogic.DAYS_PER_WEEK).toFloat()
        var index = 0
        children.forEach { v ->
            val iLeft = index * iWidth
            v.layout(iLeft.toInt(), 0, (iLeft + iWidth).toInt(), height)
            index++
        }
    }
}
