package com.kldaji.presentation.ui.client

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.kldaji.presentation.databinding.FragmentReadClientBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.BigPictureAdapter
import com.kldaji.presentation.ui.client.entity.PictureItemView
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
    private val requestPermissionCallback = registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
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
        requestPermission()
        return binding.root
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                val pictureUris = navArgs.client?.pictures?.map {
                    PictureItemView.PictureItem(it, it)
                } ?: listOf()
                Log.i(TAG, pictureUris.toString())
                val bigPictureAdapter = BigPictureAdapter(pictureUris)
                binding.vpReadClient.adapter = bigPictureAdapter
                TabLayoutMediator(binding.tlReadClient, binding.vpReadClient) { tab, position -> }
                    .attach()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // explain with UI why the permission is needed
                requestPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            else -> {
                requestPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, navArgs.client.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
