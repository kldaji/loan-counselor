package com.kldaji.presentation.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.kldaji.presentation.databinding.FragmentDateBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.calendar.adapter.DateScheduledClientAdapter
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class DateFragment : Fragment() {

    companion object {
        private const val TIMESTAMP = "timestamp"

        fun newInstance(timestamp: Long) = DateFragment().apply {
            val bundle = Bundle()
            bundle.putLong(TIMESTAMP, timestamp)
            arguments = bundle
        }
    }

    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ClientsViewModel>()
    private var timestamp = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDateBinding.inflate(inflater, container, false)

        arguments?.let {
            timestamp = it.getLong(TIMESTAMP)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDate()
        setAdapters()
    }

    private fun showDate() {
        binding.tvDayOfWeek.text = CalendarLogic.getDayOfWeek(timestamp)
        binding.tvDate.text = CalendarLogic.getMonthAndDate(timestamp)
    }

    private fun setAdapters() {
        val meetings = viewModel.getMeetingClients(Date(timestamp))
        val runs = viewModel.getRunClients(Date(timestamp))
        val meetingsAdapter = DateScheduledClientAdapter(true, meetings)
        val runsAdapter = DateScheduledClientAdapter(false, runs)
        val adapters = ConcatAdapter(meetingsAdapter, runsAdapter)
        binding.rvDate.adapter = adapters
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
