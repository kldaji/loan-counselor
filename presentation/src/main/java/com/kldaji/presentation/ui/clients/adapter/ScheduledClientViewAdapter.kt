package com.kldaji.presentation.ui.clients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.clients.entity.ScheduledClientView
import com.kldaji.presentation.databinding.ItemScheduledClientBinding

class ScheduledClientViewAdapter(private val itemClickCallback: ItemClickCallback) :
    RecyclerView.Adapter<ScheduledClientViewAdapter.ScheduledClientViewHolder>() {
    private lateinit var items: List<ScheduledClientView>

    fun setItems(_items: List<ScheduledClientView>) {
        items = _items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledClientViewHolder {
        val binding = DataBindingUtil.inflate<ItemScheduledClientBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_scheduled_client,
            parent,
            false)
        return ScheduledClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduledClientViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickCallback.onItemClick(position)
        }
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
        fun onItemClick(index: Int)
    }
}
