package com.kldaji.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kldaji.domain.ClientViewItem
import com.kldaji.presentation.databinding.FragmentSearchBinding
import com.kldaji.presentation.ui.ClientsViewModel
import com.kldaji.presentation.ui.search.adapter.SearchResultAdapter
import com.kldaji.presentation.util.ContextExtensions.hideKeyBoard
import com.kldaji.presentation.util.ContextExtensions.showKeyBoard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchResultAdapter
    private val viewModel by activityViewModels<ClientsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = SearchResultAdapter(listOf(ClientViewItem.EmptyItem()))
        binding.rvSearchResult.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditTextFocus()
        setListeners()
        setObservers()
    }

    private fun setEditTextFocus() {
        binding.tieSearch.requestFocus()
        requireContext().showKeyBoard(binding.tieSearch)
    }

    private fun setListeners() {
        binding.tvSearchCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        // TextChangeListener -> will use RxJava debounce
        binding.tieSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                requireContext().hideKeyBoard(v)
                v.clearFocus()
                viewModel.getClientsByName(binding.tieSearch.text.toString())
                true
            } else false
        }
    }

    private fun setObservers() {
        viewModel.clientsByName.observe(viewLifecycleOwner) { clients ->
            if (clients.isEmpty()) adapter.setResults(listOf(ClientViewItem.EmptyItem()))
            else adapter.setResults(clients.map { ClientViewItem.ClientItem(it) })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearClientsByName()
        _binding = null
    }
}
