package com.kldaji.domain

sealed class ClientViewItem {
    abstract val id: Long

    data class ClientItem(val client: Client, override val id: Long = client.id): ClientViewItem()
    object HeaderItem: ClientViewItem() {
        override val id: Long = Long.MIN_VALUE
    }
}
