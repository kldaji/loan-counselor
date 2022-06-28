package com.kldaji.presentation.ui.clients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.Client
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.ItemClientBinding
import com.kldaji.presentation.databinding.ItemClientsHeaderBinding
import com.kldaji.presentation.ui.clients.ClientsFragmentDirections

class ClientAdapter : ListAdapter<ClientViewItem, RecyclerView.ViewHolder>(diff) {

    fun submitListWithHeader(clients: List<Client>) {
        submitList(listOf(ClientViewItem.HeaderItem("고객 ${clients.size}")) + clients.map {
            ClientViewItem.ClientItem(it)
        })
    }

    fun submitListScheduledClients(clients: List<Client>) {
        submitList(clients.flatMap {
            // text should be meeting time
            listOf(ClientViewItem.HeaderItem("${it.meeting}", it.meeting),
                ClientViewItem.ClientItem(it))
        })
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
            is HeaderItemViewHolder -> {
                val headerItem = getItem(position) as ClientViewItem.HeaderItem
                holder.bind(headerItem)
            }
            is ClientItemViewHolder -> {
                val clientItem = getItem(position) as ClientViewItem.ClientItem
                holder.bind(clientItem)
            }
        }
    }

    class HeaderItemViewHolder(private val binding: ItemClientsHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(headerItem: ClientViewItem.HeaderItem) {
            binding.tvSubTitle.text = headerItem.text
        }
    }

    class ClientItemViewHolder(private val binding: ItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { v ->
                binding.client?.let {
                    val direction =
                        ClientsFragmentDirections.actionClientsFragmentToReadClientFragment(it)
                    v.findNavController().navigate(direction)
                }
            }
        }

        fun bind(clientItem: ClientViewItem.ClientItem) {
            binding.client = clientItem.client
            binding.executePendingBindings()
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
