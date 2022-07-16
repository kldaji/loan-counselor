package com.kldaji.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.Client
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.ItemClientBinding
import com.kldaji.presentation.ui.clients.ClientsFragmentDirections
import com.kldaji.presentation.ui.search.SearchFragmentDirections

class SearchResultAdapter(private var results: List<ClientViewItem.ClientItem>) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    fun setResults(clients: List<Client>) {
        results = clients.map { ClientViewItem.ClientItem(it) }
        notifyDataSetChanged()
    }

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

        init {
            binding.setClickListener { v ->
                binding.client?.let {
                    val direction =
                        SearchFragmentDirections.actionSearchFragmentToReadClientFragment(it)
                    v.findNavController().navigate(direction)
                }
            }
        }

        fun bind(clientItem: ClientViewItem.ClientItem) {
            binding.client = clientItem.client
            binding.executePendingBindings()
        }
    }
}
