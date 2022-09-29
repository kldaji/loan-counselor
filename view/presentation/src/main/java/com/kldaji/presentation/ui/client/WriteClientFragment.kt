package com.kldaji.presentation.ui.client

import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentWriteClientBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.PictureAdapter
import com.kldaji.presentation.ui.client.entity.Mode
import com.kldaji.presentation.util.DateConverter
import com.kldaji.presentation.util.EnumConverter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException

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
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Void>
    private lateinit var getContentLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWriteClientBinding.inflate(inflater, container, false)
        navArgs.client?.let {
            binding.client = it
            binding.tieMeeting.setText(DateConverter.longToString(it.meeting))
            binding.tieRun.setText(DateConverter.longToString(it.run))
            viewModel.fetchPictures(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLaunchers()
        connectGenderDropDownAdapter()
        setDatePickerListener()
        setTimePickerListener()
        setNavigateToBack()
        setCompleteClickListener()
        setPictureAdapter()
        addPicturesObserver()
    }

    private fun setLaunchers() {
        takePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
                bitmap ?: return@registerForActivityResult

                lifecycleScope.launch {
                    if (saveToGallery(bitmap)) {
                        Toast.makeText(requireContext(),
                            "Successfully saved to gallery",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(),
                            "Failed to save image to gallery",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }

        getContentLauncher =
            registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
                uris.forEach { uri ->
                    viewModel.addPicture(uri.toString())
                }
            }
    }

    private fun saveToGallery(
        bitmap: Bitmap,
        contentResolver: ContentResolver = requireActivity().contentResolver,
        filename: String = System.currentTimeMillis().toString() + ".jpg",
        mimeType: String = "images/*",
    ): Boolean {
        val imageUri: Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        }

        return try {
            contentResolver.insert(imageUri, contentValues)?.also { uri ->
                viewModel.addPicture(uri.toString())

                contentResolver.openOutputStream(uri).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            } ?: throw IOException("Failed to create Media Store Entry")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun connectGenderDropDownAdapter() {
        val genders = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_gender, genders)
        navArgs.client?.let {
            binding.actGender.setText(EnumConverter.genderToString(it.gender), false)
        }
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
            Log.i(TAG, DateConverter.longToString(it))
            binding.tieMeeting.setText(DateConverter.longToString(it))
        }
        runDatePicker.addOnPositiveButtonClickListener {
            Log.i(TAG, DateConverter.longToString(it))
            binding.tieRun.setText(DateConverter.longToString(it))
        }
    }

    private fun createDatePicker(title: String): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
    }

    private fun setTimePickerListener() {
        val timePicker = createTimePicker()
        binding.tieMeetingTime.setOnClickListener {
            timePicker.show()
        }
    }

    private fun createTimePicker(): TimePickerDialog {
        return TimePickerDialog(requireContext(),
            { _, hourOfDay, minute ->
                binding.tieMeetingTime.setText("$hourOfDay : ${String.format("%02d", minute)}")
            },
            12,
            0,
            false)
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
                    val id = navArgs.client?.id ?: 0L
                    val client = getClientInfo(id)
                    when (navArgs.mode) {
                        Mode.CREATE -> {
                            viewModel.insertClient(client)
                            findNavController().popBackStack()
                        }
                        Mode.EDIT -> {
                            viewModel.updateClient(navArgs.client!!, client)
                            val direction =
                                WriteClientFragmentDirections.actionWriteClientFragmentToReadClientFragment(
                                    client)
                            Log.i(TAG, client.toString())
                            findNavController().navigate(direction)
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun getClientInfo(id: Long): Client {
        return Client(
            id = id,
            name = binding.tieName.text.toString(),
            birth = binding.tieBirth.text.toString(),
            phone = binding.tiePhone.text.toString(),
            loan = EnumConverter.stringToLoan(binding.rgWriteClient.checkedRadioButtonId),
            gender = EnumConverter.stringToGender(binding.actGender.text.toString()),
            meeting = DateConverter.stringToLong(binding.tieMeeting.text.toString()),
            meetingTime = binding.tieMeetingTime.text.toString(),
            run = DateConverter.stringToLong(binding.tieRun.text.toString()),
            remark = binding.tieRemark.text.toString(),
            pictures = viewModel.pictures.value ?: listOf()
        )
    }

    private fun setPictureAdapter() {
        pictureAdapter = PictureAdapter(
            object : PictureAdapter.CameraButtonClickListener { // camera button
                override fun onButtonClick(menuRes: Int) {
                    when (menuRes) {
                        R.id.take_picture -> takePhotoLauncher.launch(null)
                        else -> getContentLauncher.launch("image/*")
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setPicturesEmpty()
    }
}
