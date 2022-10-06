package com.kldaji.presentation.ui.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentReadClientBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.BigPictureAdapter
import com.kldaji.presentation.ui.client.entity.Mode
import com.kldaji.presentation.ui.client.entity.PictureItemView
import com.kldaji.presentation.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadClientFragment : Fragment() {
    companion object {
        private const val TAG = "ReadClientFragment"
    }

    private var _binding: FragmentReadClientBinding? = null
    private val binding get() = _binding!!
    private val navArgs: ReadClientFragmentArgs by navArgs()
    private val viewModel by activityViewModels<ClientsViewModel>()
    private val requestPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
            if (success) {
                Log.i(TAG, "READ EXTERNAL STORAGE is granted")
            } else {
                Log.i(TAG, "READ EXTERNAL STORAGE not granted")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReadClientBinding.inflate(inflater, container, false)
        binding.client = navArgs.client
        binding.tieMeeting.setText(DateConverter.longToString(navArgs.client.meeting))
        binding.tieRun.setText(DateConverter.longToString(navArgs.client.run))
        val pictureUris = navArgs.client.pictures.map {
            PictureItemView.PictureItem(it, it)
        }
        val bigPictureAdapter = BigPictureAdapter(pictureUris)
        binding.vpReadClient.adapter = bigPictureAdapter
        TabLayoutMediator(binding.tlReadClient, binding.vpReadClient) { tab, position -> }
            .attach()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOverFlowMenuListener()
        setNavigateIconListener()
    }

    private fun setOverFlowMenuListener() {
        binding.tbReadClient.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    val direction =
                        ReadClientFragmentDirections.actionReadClientFragmentToWriteClientFragment(
                            Mode.EDIT, navArgs.client)
                    findNavController().navigate(direction)
                    true
                }
                R.id.delete -> {
                    viewModel.deleteClient(navArgs.client)
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
    }

    private fun setNavigateIconListener() {
        binding.tbReadClient.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
