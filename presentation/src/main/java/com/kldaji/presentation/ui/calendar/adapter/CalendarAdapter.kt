package com.kldaji.presentation.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.databinding.ItemCalendarBinding
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(ItemCalendarBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun getItemId(position: Int): Long {
        return CalendarLogic.getFirstDateOfMonthTimestamp(position - START_POSITION)
    }

    class CalendarViewHolder(private val binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.monthView.addDateItemViews(Date(itemId), CalendarLogic.getDateList(Date(itemId)))
        }
    }
}
