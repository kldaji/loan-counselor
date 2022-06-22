package com.kldaji.presentation.ui.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentWriteClientBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.PictureAdapter
import com.kldaji.presentation.util.DateConverter
import com.kldaji.presentation.util.EnumConverter
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs

@AndroidEntryPoint
class WriteClientFragment : Fragment() {
    companion object {
        private const val TAG = "WriteClientFragment"
    }

    private var _binding: FragmentWriteClientBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ClientsViewModel>()
    private val navArgs: WriteClientFragmentArgs by navArgs()
    private lateinit var pictureAdapter: PictureAdapter

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
        setNavigateToBack()
        setCompleteClickListener()
        setPictureAdapter()
        setClient()
        addPicturesObserver()
    }

    private fun connectGenderDropDownAdapter() {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_gender, viewModel.genders)
        binding.actGender.setAdapter(adapter)
    }

    private fun setDatePickerListener() {
        val meetingDatePicker = createDatePicker("Select meeting date")
        val runDatePicker = createDatePicker("Select run date")
        binding.tieMeeting.setOnClickListener {
            meetingDatePicker.show(parentFragmentManager, "Meeting")
        }
        binding.tieRun.setOnClickListener {
            runDatePicker.show(parentFragmentManager, "Run")
        }
        meetingDatePicker.addOnPositiveButtonClickListener {
            Log.i(TAG, DateConverter.longToString(requireContext(), it))
            binding.tieMeeting.setText(DateConverter.longToString(requireContext(), it))
        }
        runDatePicker.addOnPositiveButtonClickListener {
            Log.i(TAG, DateConverter.longToString(requireContext(), it))
            binding.tieRun.setText(DateConverter.longToString(requireContext(), it))
        }
    }

    private fun createDatePicker(title: String): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
    }

    private fun setNavigateToBack() {
        binding.tbWriteClient.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setCompleteClickListener() {
        binding.tbWriteClient.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.complete -> {
                    val client = getClientInfo()
                    Log.i(TAG, client.toString())
                    viewModel.insertClient(client)
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
    }

    private fun getClientInfo(): Client {
        return Client(
            name = binding.tieName.text.toString(),
            birth = binding.tieBirth.text.toString(),
            phone = binding.tiePhone.text.toString(),
            loan = EnumConverter.stringToLoan(binding.rgWriteClient.checkedRadioButtonId),
            gender = EnumConverter.stringToGender(binding.actGender.text.toString()),
            meeting = DateConverter.stringToLong(requireContext(),
                binding.tieMeeting.text.toString()),
            run = DateConverter.stringToLong(requireContext(), binding.tieRun.text.toString()),
            remark = binding.tieRemark.text.toString(),
            pictures = viewModel.client.value?.pictures ?: listOf()
        )
    }

    private fun setPictureAdapter() {
        pictureAdapter = PictureAdapter(object : PictureAdapter.ButtonClickListener {
            override fun onButtonClick(uri: String) {
                // delete picture
            }
        })
        binding.rvWriteClient.adapter = pictureAdapter
    }

    private fun setClient() {
        viewModel.setClient(navArgs.client)
    }

    private fun addPicturesObserver() {
        viewModel.client.observe(viewLifecycleOwner) {
            pictureAdapter.submitListWithHeader(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
