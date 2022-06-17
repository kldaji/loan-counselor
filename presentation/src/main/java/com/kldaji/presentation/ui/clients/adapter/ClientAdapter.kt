package com.kldaji.presentation.ui.clients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.Client
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.ItemClientBinding
import com.kldaji.presentation.databinding.ItemClientsHeaderBinding

class ClientAdapter : ListAdapter<ClientViewItem, RecyclerView.ViewHolder>(diff) {

    fun submitListWithHeader(clients: List<Client>) {
        submitList(listOf(ClientViewItem.HeaderItem) + clients.map { ClientViewItem.ClientItem(it) })
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ClientViewItem.HeaderItem -> ITEM_HEADER
            is ClientViewItem.ClientItem -> ITEM_CLIENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_HEADER -> HeaderItemViewHolder(ItemClientsHeaderBinding.inflate(LayoutInflater.from(
                parent.context), parent, false))
            else -> ClientItemViewHolder(ItemClientBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderItemViewHolder -> holder.bind(currentList.size - 1) // exclude header
            is ClientItemViewHolder -> {
                val clientItem = getItem(position) as ClientViewItem.ClientItem
                holder.bind(clientItem.client)
            }
        }
    }

    class HeaderItemViewHolder(private val binding: ItemClientsHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(numberOfClient: Int) {
            binding.tvSubTitle.text = "고객 $numberOfClient"
        }
    }

    class ClientItemViewHolder(private val binding: ItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(_client: Client) {
            binding.client = _client
        }
    }

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_CLIENT = 1

        private val diff = object : DiffUtil.ItemCallback<ClientViewItem>() {
            override fun areItemsTheSame(
                oldItem: ClientViewItem,
                newItem: ClientViewItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ClientViewItem,
                newItem: ClientViewItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
