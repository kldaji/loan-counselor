package com.kldaji.presentation.ui.client

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kldaji.presentation.databinding.FragmentFullPictureBinding

class FullPictureFragment : Fragment() {
    private var _binding: FragmentFullPictureBinding? = null
    private val binding get() = _binding!!
    private val navArgs: FullPictureFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFullPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImageUri()
    }

    private fun setImageUri() {
        try {
            val uri = Uri.parse(navArgs.uri)
//            binding.pvFullPicture.setImageURI(uri)
        } catch (e: NullPointerException) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
