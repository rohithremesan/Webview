package com.example.webview.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.webview.R
import com.example.webview.databinding.FragmentBottomSheetBinding
import com.example.webview.ui.utility.showSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomMenuFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsBtn.setOnClickListener {
            showSnackbar(requireActivity().findViewById(R.id.main),getString(R.string.settings_clicked))
            dismiss()

        }

        binding.homeBtn.setOnClickListener {
            showSnackbar(requireActivity().findViewById(R.id.main),getString(R.string.home_clicked))
            dismiss()
        }

    }
}