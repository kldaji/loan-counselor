package com.kldaji.presentation.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.kldaji.presentation.databinding.FragmentPhotoViewPagerBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.PhotoViewAdapter
import com.kldaji.presentation.ui.client.entity.PictureItemView

class PhotoViewPagerFragment : Fragment() {
    private var _binding: FragmentPhotoViewPagerBinding? = null
    private val binding get() = _binding!!
    private val navArgs: PhotoViewPagerFragmentArgs by navArgs()
    private val viewModel by activityViewModels<ClientsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhotoViewPagerBinding.inflate(inflater, container, false)
        val pictureUris = viewModel.pictures.value?.map {
            PictureItemView.PictureItem(it, it)
        } ?: listOf()
        val adapter = PhotoViewAdapter(pictureUris)
        binding.vpFullPicture.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachTabLayout()
    }

    private fun attachTabLayout() {
        TabLayoutMediator(binding.tlFullPicture, binding.vpFullPicture) { tab, position -> }
            .attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
