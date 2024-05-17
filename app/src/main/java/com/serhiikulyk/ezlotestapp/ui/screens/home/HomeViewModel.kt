package com.serhiikulyk.ezlotestapp.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiikulyk.ezlotestapp.data.repository.DevicesRepository
import com.serhiikulyk.ezlotestapp.enum.getPlatformIcon
import com.serhiikulyk.ezlotestapp.ext.Prefs
import com.serhiikulyk.ezlotestapp.ui.common.ProfileState
import com.serhiikulyk.ezlotestapp.ui.common.myProfileState
import com.serhiikulyk.ezlotestapp.utils.result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TASK : MVVM Architecture Component Usage is a must
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val devicesRepository: DevicesRepository,
    private val prefs: Prefs
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun checkToFetchTestItems() {
        if (!prefs.isSynced) {
            fetchTestItems()
        }
    }

    private fun fetchTestItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val res = result {
                devicesRepository.fetchAndSaveTestItems()
            }
            _uiState.update { it.copy(isLoading = false) }
            if (res.isSuccess) {
                if (!prefs.isSynced) {
                    prefs.isSynced = true
                }
            } else if (res.isFailure) {
                _uiState.update { it.copy(error = res.exceptionOrNull()?.localizedMessage) }
            }
        }
    }

    fun collectDevices() {
        viewModelScope.launch {
            devicesRepository.getDevicesStream().map { list ->
                list.map { entity ->
                    DeviceItem(
                        icon = getPlatformIcon(entity.platform),
                        title = entity.title.orEmpty(),
                        sn = entity.pkDevice
                    )
                }.sortedBy { it.sn } // TASK 2: The list must be sorted by PK_Device(this is the same with SN from the mocks)
            }.collect { deviceItems ->
                _uiState.update { it.copy(items = deviceItems) }
            }
        }
    }

    fun reset() {
        fetchTestItems()
    }

    fun onErrorShow() {
        _uiState.update { it.copy(error = null) }
    }

    fun delete(deviceItem: DeviceItem) {
        viewModelScope.launch {
            devicesRepository.deleteDevice(deviceItem.sn)
        }
    }

}

data class HomeUiState(
    val profile: ProfileState = myProfileState,
    val items: List<DeviceItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


data class DeviceItem(
    @DrawableRes val icon: Int,
    val title: String,
    val sn: Int
)

