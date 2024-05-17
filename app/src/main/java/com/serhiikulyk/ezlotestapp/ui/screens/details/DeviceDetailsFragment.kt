package com.serhiikulyk.ezlotestapp.ui.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.serhiikulyk.ezlotestapp.R
import com.serhiikulyk.ezlotestapp.databinding.FragmentDeviceDetailsBinding
import com.serhiikulyk.ezlotestapp.ext.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeviceDetailsFragment : Fragment() {

    private var _binding: FragmentDeviceDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DeviceDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        collectStates()
    }

    private fun initUi() {
        initToolbar()
        initTitleEdit()
        initSave()
    }

    private fun initTitleEdit() {
        binding.titleEdit.doAfterTextChanged {
            viewModel.onTitleChanged(it.toString())
        }
    }

    private fun initSave() {
        binding.save.setOnClickListener {
            viewModel.save(binding.titleEdit.text.toString())
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun collectStates() {
        collectIsEditMode()
        collectProfileState()
        collectDeviceState()
        collectTitleState()
        collectTitleErrorState()
        collectIsSavedState()
    }

    private fun collectIsEditMode() {
        lifecycleScope.launch {
            viewModel.uiState.map { it.isEditMode }.collect { state ->
                with(binding) {
                    toolbar.setTitle(if (state) R.string.edit_device_title else R.string.device_details_title)
                    title.setVisible(!state)
                    titleInput.setVisible(state)
                    save.setVisible(state)
                }
            }
        }
    }

    private fun collectProfileState() {
        lifecycleScope.launch {
            viewModel.uiState.map { it.profile }.collect { state ->
                with(binding.profile) {
                    avatar.setImageResource(state.avatarRes)
                    name.setText(state.fullNameRes)
                }
            }
        }
    }

    private fun collectTitleState() {
        lifecycleScope.launch {
            viewModel.uiState.mapNotNull { it.title }.collect { state ->
                with(binding) {
                    if (titleEdit.text.toString() != state) {
                        titleEdit.setText(state)
                    }
                    title.text = state
                }
            }
        }
    }

    private fun collectDeviceState() {
        lifecycleScope.launch {
            viewModel.uiState.mapNotNull { it.device }.collect { state ->
                with(binding) {
                    icon.setImageResource(state.icon)
                    sn.text = getString(R.string.device_sn, state.sn)
                    macAddress.text = getString(R.string.device_mac_address, state.macAddress)
                    firmware.text = getString(R.string.device_firmware, state.firmware)
                    model.text = getString(R.string.device_model, state.model)
                }
            }
        }
    }

    private fun collectTitleErrorState() {
        lifecycleScope.launch {
            viewModel.uiState.map { it.titleError }.collect { state ->
                binding.titleInput.error = state?.let { getString(it) }
            }
        }
    }

    private fun collectIsSavedState() {
        lifecycleScope.launch {
            viewModel.uiState
                .map { it.isSaved }
                .filter { it }.collect {
                    Toast.makeText(
                        requireContext(),
                        "Device title was edited successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.onSaved()
                    findNavController().popBackStack()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up binding
        _binding = null
    }

}