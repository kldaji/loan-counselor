package com.kldaji.presentation.ui.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentClientsBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.client.entity.Mode
import com.kldaji.presentation.ui.clients.adapter.ClientAdapter
import com.kldaji.presentation.ui.clients.adapter.ScheduledClientViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientsFragment : Fragment() {
    companion object {
        private const val TAG = "ClientsFragment"
    }

    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ClientsViewModel>()
    private lateinit var clientAdapter: ClientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentClientsBinding.inflate(inflater, container, false)
        val scheduledClientAdapter = ScheduledClientViewAdapter()
        scheduledClientAdapter.setItems(viewModel.scheduledClientViews)
        clientAdapter = ClientAdapter()
        val adapters = ConcatAdapter(scheduledClientAdapter, clientAdapter)
        binding.rvClients.adapter = adapters
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToOtherFragments()
        fetchClients()
        addClientsObserver()
        navigateToCalendarFragment()
    }

    private fun navigateToOtherFragments() {
        binding.tbClients.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    this.findNavController().navigate(R.id.searchFragment)
                    true
                }
                R.id.add_client -> {
                    val direction =
                        ClientsFragmentDirections.actionClientsFragmentToWriteClientFragment(mode = Mode.CREATE)
                    this.findNavController().navigate(direction)
                    true
                }
                R.id.setting -> {
                    this.findNavController().navigate(R.id.settingFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchClients() {
        viewModel.fetchClients()
    }

    private fun addClientsObserver() {
        viewModel.clients.observe(viewLifecycleOwner) {
            clientAdapter.submitListWithHeader(it)
        }
    }

    private fun navigateToCalendarFragment() {
        binding.fabCalendar.setOnClickListener {
            findNavController().navigate(R.id.calendarFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
