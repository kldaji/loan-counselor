package com.kldaji.presentation.ui.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.MarginPageTransformer
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

        setDialogLayout()
        setViewPagerPageTransformer()
    }

    private fun setDialogLayout() {
        val windowManager =
            requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val rect = windowManager.currentWindowMetrics.bounds
        println(rect.width())
        println(rect.height())
        val height = resources.getDimensionPixelSize(R.dimen.date_dialog_height)
        dialog?.window?.setLayout(rect.width(), height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setViewPagerPageTransformer() {
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.page_margin)
        binding.vpDialogFragmentDate.setPageTransformer(MarginPageTransformer(pageMargin))
        binding.vpDialogFragmentDate.offscreenPageLimit = 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
