package com.kldaji.presentation.ui.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kldaji.presentation.databinding.ItemCalendarToolbarBinding
import com.kldaji.presentation.util.CalendarLogic

class ItemCalendarToolbar(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private var binding: ItemCalendarToolbarBinding

    init {
        binding = ItemCalendarToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setOnNavigateIconClickListener(onClickListener: OnClickListener) {
        binding.ivCalendarToolbarBack.setOnClickListener(onClickListener)
    }

    fun setToolbarTitle(monthAmount: Int) {
        binding.tvCalendarToolbar.text = "${CalendarLogic.getMonth(monthAmount)}월"
    }
}
