package com.kldaji.presentation.ui.scheduledClients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kldaji.presentation.databinding.FragmentScheduledClientsBinding

class ScheduledClientsFragment : Fragment() {
    companion object {
        private const val TAG = "ScheduledClientsFragment"
    }

    private var _binding: FragmentScheduledClientsBinding? = null
    private val binding get() = _binding!!
    private val navArgs: ScheduledClientsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScheduledClientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, navArgs.index.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
