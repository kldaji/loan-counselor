package com.kldaji.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.Client
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.ItemClientBinding
import com.kldaji.presentation.databinding.ItemEmptyBinding

class SearchResultAdapter(private var results: List<ClientViewItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val EMPTY_TYPE = 0
        const val CLIENT_TYPE = 1
    }

    fun setResults(clientViewItems: List<ClientViewItem>) {
        results = clientViewItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (results[position] is ClientViewItem.EmptyItem) EMPTY_TYPE
        else CLIENT_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_TYPE -> EmptyViewHolder(ItemEmptyBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
            else -> SearchResultViewHolder(ItemClientBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchResultViewHolder -> {
                val clientItem = results[position] as ClientViewItem.ClientItem
                holder.bind(clientItem)
            }
        }
    }

    override fun getItemCount(): Int = results.size


    class EmptyViewHolder(private val binding: ItemEmptyBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SearchResultViewHolder(private val binding: ItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clientItem: ClientViewItem.ClientItem) {
            binding.client = clientItem.client
        }
    }
}
