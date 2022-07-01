package com.kldaji.presentation.ui.calendar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kldaji.presentation.ui.calendar.MonthFragment
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class CalendarAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val timestamp = getItemId(position)
        return MonthFragment.newInstance(timestamp)
    }

    override fun getItemId(position: Int): Long {
        return CalendarLogic.getFirstDateOfMonthTimestamp(position - START_POSITION)
    }

    override fun containsItem(itemId: Long): Boolean {
        return CalendarLogic.isFirstDateOfMonth(Date(itemId))
    }
}
