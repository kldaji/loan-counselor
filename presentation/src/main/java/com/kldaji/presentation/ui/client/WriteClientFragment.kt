package com.kldaji.presentation.ui.client

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentWriteClientBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.PictureAdapter
import com.kldaji.presentation.util.DateConverter
import com.kldaji.presentation.util.EnumConverter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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
    private val getContentCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.data?.let {
                requireActivity().contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                viewModel.addPicture(it)
            }
        }
    private var pictureUri: Uri? = null
    private val takePictureCallback =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Log.i(TAG, pictureUri.toString())
                viewModel.addPicture(pictureUri)
            }
        }
    private val requestPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.i(TAG, "permission is granted")
            } else {
                Log.i(TAG, "permission is not granted")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWriteClientBinding.inflate(inflater, container, false)
        navArgs.client?.let {
            binding.client = it
            binding.tieMeeting.setText(DateConverter.longToString(requireContext(), it.meeting))
            binding.tieRun.setText(DateConverter.longToString(requireContext(), it.run))
        }
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
            pictures = viewModel.pictures.value ?: listOf()
        )
    }

    private fun setPictureAdapter() {
        pictureAdapter = PictureAdapter(
            object : PictureAdapter.CameraButtonClickListener { // camera button
                override fun onButtonClick(menuRes: Int) {
                    when (menuRes) {
                        R.id.take_picture -> requestPermission()
                        else -> {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                            intent.type = "image/*"
                            getContentCallback.launch(intent)
                        }
                    }
                }
            },
            object : PictureAdapter.ButtonClickListener { // delete button
                override fun onButtonClick(uri: String) {
                    viewModel.deletePicture(uri)
                }
            })
        binding.rvWriteClient.adapter = pictureAdapter
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                val pictureFile = createImageFile()
                Log.i(TAG, pictureFile.absolutePath)
                pictureUri = FileProvider.getUriForFile(requireContext(),
                    "com.kldaji.loancounselor.fileprovider",
                    pictureFile)
                takePictureCallback.launch(pictureUri)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // explain with UI why the permission is needed
                requestPermissionCallback.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestPermissionCallback.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val storageDir = requireContext().filesDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    private fun setClient() {
        viewModel.fetchPictures(navArgs.client)
    }

    private fun addPicturesObserver() {
        viewModel.pictures.observe(viewLifecycleOwner) {
            Log.i(TAG, it.toString())
            pictureAdapter.submitListWithHeader(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
