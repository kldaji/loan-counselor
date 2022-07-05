package com.kldaji.presentation.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kldaji.presentation.R
import com.kldaji.presentation.databinding.DialogFragmentDateBinding
import com.kldaji.presentation.ui.calendar.adapter.DateAdapter

class DateDialogFragment : DialogFragment() {

    companion object {
        private const val TIMESTAMP = "timestamp"

        fun newInstance(timestamp: Long) = DateDialogFragment().apply {
            val bundle = Bundle()
            bundle.putLong(TIMESTAMP, timestamp)
            arguments = bundle
        }
    }

    private var _binding: DialogFragmentDateBinding? = null
    private val binding: DialogFragmentDateBinding get() = _binding!!
    private var timestamp = 0L

    override fun getTheme(): Int = R.style.FullWidthDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentDateBinding.inflate(inflater, container, false)

        arguments?.let {
            timestamp = it.getLong(TIMESTAMP)
        }

        val adapter = DateAdapter(requireActivity(), timestamp)
        binding.vpDialogFragmentDate.adapter = adapter
        binding.vpDialogFragmentDate.setCurrentItem(DateAdapter.START_POSITION, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val height = resources.getDimensionPixelSize(R.dimen.date_dialog_height)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
