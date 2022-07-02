package com.kldaji.presentation.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.kldaji.presentation.databinding.FragmentCalendarBinding
import com.kldaji.presentation.ui.calendar.adapter.CalendarAdapter

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val adapter = CalendarAdapter(requireActivity())
        binding.vpCalendar.adapter = adapter
        binding.vpCalendar.setCurrentItem(CalendarAdapter.START_POSITION, false)
        binding.tbCalendar.setToolbarTitle(0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setPageChangeListener()
    }

    private fun setClickListeners() {
        binding.tbCalendar.setOnNavigateIconClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setPageChangeListener() {
        pageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                println(position - CalendarAdapter.START_POSITION)
                binding.tbCalendar.setToolbarTitle(position - CalendarAdapter.START_POSITION)
            }
        }
        binding.vpCalendar.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.vpCalendar.unregisterOnPageChangeCallback(pageChangeCallback)
        _binding = null
    }
}
