package com.kldaji.presentation.ui.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.databinding.ItemBigPictureBinding
import com.kldaji.presentation.ui.client.entity.PictureItemView

class BigPictureAdapter(private val pictureUris: List<PictureItemView.PictureItem>) :
    RecyclerView.Adapter<BigPictureAdapter.BigPictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigPictureViewHolder {
        return BigPictureViewHolder(ItemBigPictureBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: BigPictureViewHolder, position: Int) {
        holder.bind(pictureUris[position])
    }

    override fun getItemCount(): Int = pictureUris.size

    class BigPictureViewHolder(private val binding: ItemBigPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pictureItem: PictureItemView.PictureItem) {
            binding.pictureItem = pictureItem
            binding.executePendingBindings()
        }
    }
}
