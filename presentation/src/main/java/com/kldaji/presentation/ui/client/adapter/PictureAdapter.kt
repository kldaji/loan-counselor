package com.kldaji.presentation.ui.client.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.ItemPictureBinding
import com.kldaji.presentation.databinding.ItemPictureHeaderBinding
import com.kldaji.presentation.ui.client.entity.PictureItemView

class PictureAdapter(
    private val cameraButtonClickListener: CameraButtonClickListener,
    private val deleteButtonClickListener: ButtonClickListener,
) :
    ListAdapter<PictureItemView, RecyclerView.ViewHolder>(diff) {

    fun submitListWithHeader(pictures: List<String>) {
        submitList(listOf(PictureItemView.Header) + pictures.map {
            PictureItemView.PictureItem(it, it)
        })
    }

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

    private inner class HeaderViewHolder(private val binding: ItemPictureHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivPictureHeader.setOnClickListener {
                showPopupMenu(it)
            }
        }

        private fun showPopupMenu(anchorView: View) {
            val popupMenu = PopupMenu(binding.root.context, anchorView)
            popupMenu.menuInflater.inflate(R.menu.camera_button, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                cameraButtonClickListener.onButtonClick(menuItem.itemId)
                true
            }
            popupMenu.show()
        }
    }

    private inner class PictureViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setImageClickListener {
                // image click
            }
            binding.ibPicture.setOnClickListener {
                deleteButtonClickListener.onButtonClick((getItem(bindingAdapterPosition) as PictureItemView.PictureItem).uri)
            }
        }

        fun bind(uriString: String) {
            try {
                val uri = Uri.parse(uriString)
                binding.ivPicture.setImageURI(uri)
                binding.ivPicture.clipToOutline = true
            } catch (e: NullPointerException) {

            }
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

    interface CameraButtonClickListener {
        fun onButtonClick(menuRes: Int)
    }

    interface ButtonClickListener {
        fun onButtonClick(uri: String?)
    }
}
