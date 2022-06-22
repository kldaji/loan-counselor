package com.kldaji.presentation.ui.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.databinding.ItemPictureBinding
import com.kldaji.presentation.databinding.ItemPictureHeaderBinding
import com.kldaji.presentation.ui.client.entity.PictureItemView

class PictureAdapter : ListAdapter<PictureItemView, RecyclerView.ViewHolder>(diff) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_HEADER
            else -> ITEM_PICTURE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_HEADER -> HeaderViewHolder(ItemPictureHeaderBinding.inflate(LayoutInflater.from(
                parent.context), parent, false))
            else -> PictureViewHolder(ItemPictureBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PictureViewHolder -> {
                val pictureItem = getItem(position) as PictureItemView.PictureItem
                holder.bind(pictureItem.uri)
            }
        }
    }

    class HeaderViewHolder(private val binding: ItemPictureHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class PictureViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setImageClickListener {
                // image click
            }
            binding.setButtonClickListener {
                // delete button click
            }
        }

        fun bind(uri: String) {
            // set image uri
        }
    }

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_PICTURE = 1

        val diff = object : DiffUtil.ItemCallback<PictureItemView>() {
            override fun areItemsTheSame(
                oldItem: PictureItemView,
                newItem: PictureItemView,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PictureItemView,
                newItem: PictureItemView,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
