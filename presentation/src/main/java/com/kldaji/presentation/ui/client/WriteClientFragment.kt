package com.kldaji.presentation.ui.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentWriteClientBinding
import com.kldaji.presentation.ui.ClientsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteClientFragment : Fragment() {
    companion object {
        private const val TAG = "WriteClientFragment"
    }

    private var _binding: FragmentWriteClientBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ClientsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWriteClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectGenderDropDownAdapter()
        setDatePickerListener()
    }

    private fun connectGenderDropDownAdapter() {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_gender, viewModel.genders)
        binding.actGender.setAdapter(adapter)
    }

    private fun setDatePickerListener() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
        binding.tieMeeting.setOnClickListener {
            datePicker.show(parentFragmentManager, "Meeting")
        }
        binding.tieRun.setOnClickListener {
            datePicker.show(parentFragmentManager, "Meeting")
        }

        datePicker.addOnPositiveButtonClickListener {
            Log.i(TAG, it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
