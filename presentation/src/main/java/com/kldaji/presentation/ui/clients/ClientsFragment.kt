package com.kldaji.presentation.ui.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.FragmentClientsBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.clients.adapter.ClientAdapter
import com.kldaji.presentation.ui.clients.adapter.ScheduledClientViewAdapter

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToOtherFragments()
        connectAdapters()
        fetchClients()
        addClientsObserver()
    }

    private fun navigateToOtherFragments() {
        binding.tbClients.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    this.findNavController().navigate(R.id.searchFragment)
                    true
                }
                R.id.add_client -> {
                    this.findNavController().navigate(R.id.clientInfoFragment)
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

    private fun connectAdapters() {
        val scheduledClientAdapter =
            ScheduledClientViewAdapter(object : ScheduledClientViewAdapter.ItemClickListener {
                override fun onItemClick(index: Int) {
                    val direction =
                        ClientsFragmentDirections.actionClientsFragmentToScheduledClientsFragment(
                            index)
                    this@ClientsFragment.findNavController()
                        .navigate(direction)
                }
            })
        scheduledClientAdapter.setItems(viewModel.scheduledClientViews)
        clientAdapter = ClientAdapter(object : ClientAdapter.ItemClickListener {
            override fun onItemClick(client: Client) {
                val direction =
                    ClientsFragmentDirections.actionClientsFragmentToClientInfoFragment(client)
                this@ClientsFragment.findNavController().navigate(direction)
            }
        })
        val adapters = ConcatAdapter(scheduledClientAdapter, clientAdapter)
        binding.rvClients.adapter = adapters
    }

    private fun addClientsObserver() {
        viewModel.clients.observe(viewLifecycleOwner) {
            clientAdapter.submitListWithHeader(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
