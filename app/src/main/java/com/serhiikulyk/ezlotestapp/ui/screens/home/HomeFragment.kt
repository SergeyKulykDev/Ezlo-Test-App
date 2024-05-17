package com.serhiikulyk.ezlotestapp.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.serhiikulyk.ezlotestapp.R
import com.serhiikulyk.ezlotestapp.const.KEY_IS_EDIT_MODE
import com.serhiikulyk.ezlotestapp.const.KEY_PK_DEVICE
import com.serhiikulyk.ezlotestapp.databinding.FragmentHomeBinding
import com.serhiikulyk.ezlotestapp.ext.setVisible
import com.serhiikulyk.ezlotestapp.ext.showMaterialAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val devicesAdapter = DevicesAdapter(
        onClick = {
            navigateToDetails(it)
        }, onLongClick = {
            showBottomSheetMenu(it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        collectStates()

        viewModel.checkToFetchTestItems()
        viewModel.collectDevices()
    }

    private fun initUi() {
        initToolbar()
        initDevices()
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.reset) {
                viewModel.reset()
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun initDevices() {
        binding.devices.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = devicesAdapter
        }
    }

    private fun collectStates() {
        collectProfileState()
        collectIsLoadingState()
        collectItemsState()
        collectErrorState()
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

    private fun collectIsLoadingState() {
        lifecycleScope.launch {
            viewModel.uiState.map { it.isLoading }.collect { state ->
                binding.progress.setVisible(state)
            }
        }
    }

    private fun collectItemsState() {
        lifecycleScope.launch {
            viewModel.uiState.map { it.items }.collect { state ->
                devicesAdapter.submitList(state)
                binding.emptyState.setVisible(state.isEmpty())
            }
        }
    }

    private fun collectErrorState() {
        lifecycleScope.launch {
            viewModel.uiState.mapNotNull { it.error }.collect { state ->
                Snackbar.make(requireContext(), binding.root, state, Snackbar.LENGTH_LONG).show()
                viewModel.onErrorShow()
            }
        }
    }

    private fun navigateToDetails(deviceItem: DeviceItem) {
        findNavController().navigate(
            R.id.nav_details,
            args = bundleOf(KEY_PK_DEVICE to deviceItem.sn)
        )
    }

    private fun showBottomSheetMenu(deviceItem: DeviceItem) {
        val bottomSheet = BottomSheetMenuFragment()
        bottomSheet.setBottomSheetListener(object : BottomSheetMenuFragment.BottomSheetListener {
            override fun onOptionClicked(option: BottomSheetMenuAction) {
                when (option) {
                    BottomSheetMenuAction.Edit -> {
                        val args = bundleOf(
                            KEY_PK_DEVICE to deviceItem.sn,
                            KEY_IS_EDIT_MODE to true
                        )
                        findNavController().navigate(
                            R.id.nav_details, args = args
                        )
                    }

                    BottomSheetMenuAction.Delete -> {
                        showDeleteAlert(deviceItem)
                    }
                }
            }
        })
        bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
    }

    private fun showDeleteAlert(deviceItem: DeviceItem) {
        requireContext().showMaterialAlertDialog(
            title = getString(R.string.alert_delete_title, deviceItem.title),
            message = getString(R.string.alert_delete_description),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            onPositiveButtonClick = {
                viewModel.delete(deviceItem)
            },
            onNegativeButtonClick = {
                // Do nothing
            }
        )
    }

}
