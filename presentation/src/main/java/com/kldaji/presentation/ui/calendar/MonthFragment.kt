package com.kldaji.presentation.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kldaji.presentation.databinding.FragmentMonthBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMonthBinding.inflate(inflater, container, false)
        arguments?.let {
            timestamp = it.getLong(TIMESTAMP)
        }
        println("$this onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("$this onViewCreated")
        binding.monthView.addDateItemViews(Date(timestamp), CalendarLogic.getDateList(Date(timestamp)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        println("$this onDestroyView")
    }
}
