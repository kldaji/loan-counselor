package com.kldaji.presentation.ui.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.ItemPictureBinding
import com.kldaji.presentation.databinding.ItemPictureHeaderBinding
import com.kldaji.presentation.ui.client.WriteClientFragmentDirections
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
                holder.bind(pictureItem)
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
            binding.setImageClickListener { v ->
                binding.pictureItem?.let { pictureItem ->
                    val direction =
                        WriteClientFragmentDirections.actionWriteClientFragmentToPhotoViewPagerFragment(
                            pictureItem.uri)
                    v.findNavController().navigate(direction)
                }
            }
            binding.ibPicture.setOnClickListener {
                binding.pictureItem?.let { pictureItem ->
                    deleteButtonClickListener.onButtonClick(pictureItem.uri)
                }
            }
        }

        fun bind(pictureItem: PictureItemView.PictureItem) {
            binding.pictureItem = pictureItem
            binding.ivPicture.clipToOutline = true
            binding.executePendingBindings()
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
        fun onButtonClick(uri: String)
    }
}
