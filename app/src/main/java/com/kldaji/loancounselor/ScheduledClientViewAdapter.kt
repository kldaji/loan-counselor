package com.kldaji.loancounselor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.loancounselor.databinding.ItemScheduledClientBinding

class ScheduledClientViewAdapter(private val itemClickCallback: ItemClickCallback) :
    RecyclerView.Adapter<ScheduledClientViewAdapter.ScheduledClientViewHolder>() {
    private val items = mutableListOf(
        ScheduledClientView("미팅 예정 고객",
            "오늘의 미팅 예정 고객 명단 입니다!",
            R.drawable.ic_meeting_24),
        ScheduledClientView("대출실행 예정 고객",
            "30일내 대출실행 예정 고객 명단 입니다!",
            R.drawable.ic_action_24)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledClientViewHolder {
        val binding = DataBindingUtil.inflate<ItemScheduledClientBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_scheduled_client,
            parent,
            false).also { it.root.setOnClickListener { itemClickCallback.onItemClick() } }

        return ScheduledClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduledClientViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


    class ScheduledClientViewHolder(private val binding: ItemScheduledClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduledClientView) {
            binding.scheduledClientView = item
        }
    }

    interface ItemClickCallback {
        fun onItemClick()
    }
}
