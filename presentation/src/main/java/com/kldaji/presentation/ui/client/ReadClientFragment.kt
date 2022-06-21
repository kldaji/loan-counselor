package com.kldaji.presentation.ui.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kldaji.presentation.databinding.FragmentReadClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadClientFragment : Fragment() {
    companion object {
        private const val TAG = "ClientInfoFragment"
    }

    private var _binding: FragmentReadClientBinding? = null
    private val binding get() = _binding!!
    private val navArgs: ReadClientFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReadClientBinding.inflate(inflater, container, false)
        return binding.root
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
