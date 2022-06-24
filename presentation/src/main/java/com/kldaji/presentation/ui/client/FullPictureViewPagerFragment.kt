package com.kldaji.presentation.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.kldaji.presentation.databinding.FragmentFullPictureViewPagerBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.adapter.FullPictureAdapter
import com.kldaji.presentation.ui.client.entity.PictureItemView

class FullPictureViewPagerFragment : Fragment() {
    private var _binding: FragmentFullPictureViewPagerBinding? = null
    private val binding get() = _binding!!
    private val navArgs: FullPictureViewPagerFragmentArgs by navArgs()
    private val viewModel by activityViewModels<ClientsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFullPictureViewPagerBinding.inflate(inflater, container, false)
        val pictureUris = viewModel.pictures.value?.map {
            PictureItemView.PictureItem(it, it)
        } ?: listOf()
        val adapter = FullPictureAdapter(pictureUris)
        binding.vpFullPicture.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
