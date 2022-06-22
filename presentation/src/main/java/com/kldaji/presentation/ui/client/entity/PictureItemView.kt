package com.kldaji.presentation.ui.client.entity

sealed class PictureItemView {
    abstract val id: Long
    data class PictureItem(override val id: Long, val uri: String): PictureItemView()
    object Header: PictureItemView() {
        override val id: Long = Long.MIN_VALUE
    }
}
