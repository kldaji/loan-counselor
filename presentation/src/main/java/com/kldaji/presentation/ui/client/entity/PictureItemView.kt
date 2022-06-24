package com.kldaji.presentation.ui.client.entity

sealed class PictureItemView {
    abstract val id: String
    data class PictureItem(override val id: String, val uri: String): PictureItemView()
    object Header: PictureItemView() {
        override val id: String = ""
    }
}
