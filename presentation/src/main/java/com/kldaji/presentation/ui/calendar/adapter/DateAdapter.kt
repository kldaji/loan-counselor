package com.kldaji.presentation.ui.calendar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kldaji.presentation.ui.calendar.DateFragment

class DateAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        return DateFragment()
    }
}
