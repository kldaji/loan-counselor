package com.kldaji.presentation.ui.calendar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kldaji.presentation.ui.calendar.DateFragment
import com.kldaji.presentation.util.CalendarLogic

class DateAdapter(fm: FragmentActivity, private val timestamp: Long): FragmentStateAdapter(fm) {

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val mTimestamp = getItemId(position)
        return DateFragment.newInstance(mTimestamp)
    }

    override fun getItemId(position: Int): Long {
        return CalendarLogic.getTimestamp(timestamp, position - START_POSITION)
    }

    override fun containsItem(itemId: Long): Boolean {
//        return super.containsItem(itemId)
        return true // itemId range is not decided yet
    }
}
