package com.kldaji.presentation.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kldaji.presentation.databinding.FragmentMonthBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.util.CalendarLogic
import java.util.*

class MonthFragment : Fragment() {
    companion object {
        private const val TIMESTAMP = "timestamp"

        fun newInstance(timestamp: Long) = MonthFragment().apply {
            val bundle = Bundle()
            bundle.putLong(TIMESTAMP, timestamp)
            arguments = bundle
        }
    }

    private var _binding: FragmentMonthBinding? = null
    private val binding get() = _binding!!
    private var timestamp = 0L
    private val viewModel by activityViewModels<ClientsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMonthBinding.inflate(inflater, container, false)
        arguments?.let {
            timestamp = it.getLong(TIMESTAMP)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dates = CalendarLogic.getDateList(Date(timestamp))
        binding.monthView.addDateItemViews(
            Date(timestamp),
            dates,
            viewModel.getMeetingClientsInMonth(dates),
            viewModel.getRunClientsInMonth(dates),
            this::showDateDialog
        )
    }

    private fun showDateDialog(timestamp: Long) {
        val direction = CalendarFragmentDirections.actionCalendarFragmentToDateDialogFragment(timestamp)
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
