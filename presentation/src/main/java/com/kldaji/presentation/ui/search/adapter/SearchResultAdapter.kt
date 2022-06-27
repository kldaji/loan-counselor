package com.kldaji.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.ItemClientBinding

class SearchResultAdapter(private val results: List<ClientViewItem.ClientItem>) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(ItemClientBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size


    class SearchResultViewHolder(private val binding: ItemClientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clientItem: ClientViewItem.ClientItem) {
            binding.client = clientItem.client
        }
    }
}
