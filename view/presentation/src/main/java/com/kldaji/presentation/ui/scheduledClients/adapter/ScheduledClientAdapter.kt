package com.kldaji.presentation.ui.scheduledClients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.Client
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.ItemClientBinding
import com.kldaji.presentation.databinding.ItemScheduledClientHeaderBinding
import com.kldaji.presentation.ui.scheduledClients.ScheduledClientsFragmentDirections
import com.kldaji.presentation.util.CalendarLogic

class ScheduledClientAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val clientViewItems = mutableListOf<ClientViewItem>()

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_CLIENT = 1
    }

    fun setScheduledClients(index: Int, clients: List<Client>) {
        clientViewItems.clear()
        when (index) {
            0 -> {
                clientViewItems.addAll(clients.flatMap {
                    listOf(ClientViewItem.HeaderItem(CalendarLogic.getMonthAndDate(it.meeting) + " " + it.meetingTime),
                        ClientViewItem.ClientItem(it, it.id))
                })
            }
            1 -> {
                clientViewItems.addAll(clients.flatMap {
                    listOf(ClientViewItem.HeaderItem(CalendarLogic.getMonthAndDate(it.run)),
                        ClientViewItem.ClientItem(it, it.id))
                })
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (clientViewItems[position]) {
            is ClientViewItem.HeaderItem -> ITEM_HEADER
            is ClientViewItem.ClientItem -> ITEM_CLIENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_HEADER -> {
                ScheduledClientHeaderViewHolder(ItemScheduledClientHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
            }
            else -> { // ITEM_CLIENT
                ScheduledClientViewHolder(ItemClientBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScheduledClientHeaderViewHolder -> {
                val headerItem = clientViewItems[position] as ClientViewItem.HeaderItem
                holder.bind(headerItem)
            }
            is ScheduledClientViewHolder -> {
                val clientItem = clientViewItems[position] as ClientViewItem.ClientItem
                holder.bind(clientItem)
            }
        }
    }

    override fun getItemCount(): Int = clientViewItems.size

    class ScheduledClientHeaderViewHolder(private val binding: ItemScheduledClientHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(headerItem: ClientViewItem.HeaderItem) {
            binding.tvDate.text = headerItem.text
        }
    }


    class ScheduledClientViewHolder(private val binding: ItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { v ->
                binding.client?.let {
                    val direction =
                        ScheduledClientsFragmentDirections.actionScheduledClientsFragmentToReadClientFragment(
                            it)
                    v.findNavController().navigate(direction)
                }
            }
        }

        fun bind(clientItem: ClientViewItem.ClientItem) {
            binding.client = clientItem.client
        }
    }
}
