package com.kldaji.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.kldaji.presentation.databinding.FragmentSearchBinding
import com.kldaji.presentation.ui.search.adapter.SearchResultAdapter
import com.kldaji.presentation.util.ContextExtensions.hideKeyBoard
import com.kldaji.presentation.util.ContextExtensions.showKeyBoard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val adapter = SearchResultAdapter(listOf())
        binding.rvSearchResult.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditTextFocus()
        setListeners()
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
                true
            } else false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
