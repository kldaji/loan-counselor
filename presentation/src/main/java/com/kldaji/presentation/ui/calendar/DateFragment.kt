package com.kldaji.presentation.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kldaji.presentation.databinding.FragmentDateBinding
import com.kldaji.presentation.ui.ClientsViewModel

class DateFragment : Fragment() {

    companion object {
        private const val TIMESTAMP = "timestamp"

        fun newInstance(timestamp: Long) = DateFragment().apply {
            val bundle = Bundle()
            bundle.putLong(TIMESTAMP, timestamp)
            arguments = bundle
        }
    }

    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ClientsViewModel>()
    private var timestamp = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDateBinding.inflate(inflater, container, false)

        arguments?.let {
            timestamp = it.getLong(TIMESTAMP)
        }

        // need to add recyclerview adapter
        // the items of recyclerview is fetched from viewModel
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
