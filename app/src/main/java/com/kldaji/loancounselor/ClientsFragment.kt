package com.kldaji.loancounselor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.kldaji.loancounselor.databinding.FragmentClientsBinding

class ClientsFragment : Fragment() {
    companion object {
        private const val TAG = "ClientsFragment"
    }

    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!

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

    private fun connectAdapters() {
        val scheduledClientAdapter =
            ScheduledClientViewAdapter(object : ScheduledClientViewAdapter.ItemClickCallback {
                override fun onItemClick() {
                    this@ClientsFragment.findNavController()
                        .navigate(R.id.action_clientsFragment_to_scheduledClientsFragment)
                }
            })
        val adapters = ConcatAdapter(scheduledClientAdapter)
        binding.rvClients.adapter = adapters
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
