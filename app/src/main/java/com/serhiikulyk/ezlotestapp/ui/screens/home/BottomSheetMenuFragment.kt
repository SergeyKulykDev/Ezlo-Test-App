package com.serhiikulyk.ezlotestapp.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.serhiikulyk.ezlotestapp.databinding.BottomSheetMenuBinding

class BottomSheetMenuFragment : BottomSheetDialogFragment() {

    interface BottomSheetListener {
        fun onOptionClicked(option: BottomSheetMenuAction)
    }

    private var _binding: BottomSheetMenuBinding? = null
    private val binding get() = _binding!!

    private var listener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listeners for menu options
        binding.textViewEdit.setOnClickListener {
            listener?.onOptionClicked(BottomSheetMenuAction.Edit)
            dismiss()
        }

        binding.textViewDelete.setOnClickListener {
            listener?.onOptionClicked(BottomSheetMenuAction.Delete)
            dismiss()
        }
    }

    fun setBottomSheetListener(listener: BottomSheetListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

enum class BottomSheetMenuAction {
    Edit,
    Delete;
}