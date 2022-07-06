package com.kldaji.presentation.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.ItemDateScheduledClientBinding

class DateScheduledClientAdapter(
    private val isMeeting: Boolean,
    private val clients: List<Client>,
) :
    RecyclerView.Adapter<DateScheduledClientAdapter.DateScheduledClientViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DateScheduledClientViewHolder {
        return DateScheduledClientViewHolder(ItemDateScheduledClientBinding.inflate(LayoutInflater.from(
            parent.context), parent, false), isMeeting)
    }

    override fun onBindViewHolder(holder: DateScheduledClientViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    override fun getItemCount(): Int = clients.size

    class DateScheduledClientViewHolder(
        private val binding: ItemDateScheduledClientBinding,
        isMeeting: Boolean,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            if (isMeeting) {
                binding.vDateScheduledClient.setBackgroundResource(R.drawable.border_rect_green_16)
            } else {
                binding.vDateScheduledClient.setBackgroundResource(R.drawable.border_rect_yellow_16)
            }
        }

        fun bind(client: Client) {
            binding.client = client
        }
    }
}
