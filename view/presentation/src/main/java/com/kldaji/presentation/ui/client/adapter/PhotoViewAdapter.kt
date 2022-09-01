package com.kldaji.presentation.ui.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.databinding.ItemFullPictureBinding
import com.kldaji.presentation.ui.client.entity.PictureItemView

class PhotoViewAdapter(private val pictureUris: List<PictureItemView.PictureItem>) :
    RecyclerView.Adapter<PhotoViewAdapter.FullPictureViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullPictureViewHolder {
        return FullPictureViewHolder(ItemFullPictureBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FullPictureViewHolder, position: Int) {
        holder.bind(pictureUris[position])
    }

    override fun getItemCount(): Int = pictureUris.size

    class FullPictureViewHolder(private val binding: ItemFullPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pictureItem: PictureItemView.PictureItem) {
            binding.pictureItem = pictureItem
            binding.executePendingBindings()
        }
    }
}
