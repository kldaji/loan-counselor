package com.kldaji.domain

sealed class ClientViewItem {
    abstract val id: Long

    data class ClientItem(val client: Client, override val id: Long = client.id) : ClientViewItem()
    data class HeaderItem(val text: String, override val id: Long = Long.MIN_VALUE) :
        ClientViewItem()
}
