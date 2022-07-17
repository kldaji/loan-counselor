package com.kldaji.presentation.ui.scheduledClients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kldaji.presentation.databinding.FragmentScheduledClientsBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.scheduledClients.adapter.ScheduledClientAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduledClientsFragment : Fragment() {
    companion object {
        private const val TAG = "ScheduledClientsFragment"
    }

    private var _binding: FragmentScheduledClientsBinding? = null
    private val binding get() = _binding!!
    private val navArgs: ScheduledClientsFragmentArgs by navArgs()
    private val viewModel by activityViewModels<ClientsViewModel>()
    private lateinit var adapter: ScheduledClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScheduledClientsBinding.inflate(inflater, container, false)
        adapter = ScheduledClientAdapter()
        binding.rvScheduledClients.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchScheduledClients(navArgs.index)
        setEmptyMessage()
        setToolbarTitle()
        setClickListeners()
        setObservers()
    }

    private fun setEmptyMessage() {
        binding.tvEmptyMessage.text = if (navArgs.index == 0) "오늘 미팅 예정 고객은 없습니다."
        else "한달 내 대출 실행 예정 고객은 없습니다."
    }

    private fun setToolbarTitle() {
        binding.tbScheduledClients.title = if (navArgs.index == 0) "오늘 미팅 예정 고객" else "대출 실행 예정 고객"
    }

    private fun setClickListeners() {
        binding.tbScheduledClients.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObservers() {
        viewModel.scheduledClients.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                showEmptyMessage()
            } else {
                hideEmptyMessage()
                adapter.setScheduledClients(it)
            }
        }
    }

    private fun showEmptyMessage() {
        binding.rvScheduledClients.isInvisible = true
        binding.tvEmptyMessage.isVisible = true
    }

    private fun hideEmptyMessage() {
        binding.rvScheduledClients.isVisible = true
        binding.tvEmptyMessage.isInvisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
