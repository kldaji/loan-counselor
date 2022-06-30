package com.kldaji.presentation.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.databinding.ItemCalendarBinding

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(ItemCalendarBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = Int.MAX_VALUE


    class CalendarViewHolder(private val binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
