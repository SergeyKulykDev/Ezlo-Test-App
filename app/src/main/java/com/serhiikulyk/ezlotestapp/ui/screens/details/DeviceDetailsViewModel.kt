package com.serhiikulyk.ezlotestapp.ui.screens.details

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiikulyk.ezlotestapp.R
import com.serhiikulyk.ezlotestapp.const.KEY_IS_EDIT_MODE
import com.serhiikulyk.ezlotestapp.const.KEY_PK_DEVICE
import com.serhiikulyk.ezlotestapp.data.repository.DevicesRepository
import com.serhiikulyk.ezlotestapp.enum.getPlatformIcon
import com.serhiikulyk.ezlotestapp.ui.common.ProfileState
import com.serhiikulyk.ezlotestapp.ui.common.myProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val devicesRepository: DevicesRepository
) : ViewModel() {

    private val pkDevice = savedStateHandle.get<Int>(KEY_PK_DEVICE)!!
    private val isEditMode = savedStateHandle.get<Boolean>(KEY_IS_EDIT_MODE) ?: false

    private val _uiState = MutableStateFlow(DeviceDetailsUiState(isEditMode = isEditMode))
    val uiState = _uiState.asStateFlow()

    init {
        getDeviceDetails()
    }

    private fun getDeviceDetails() {
        viewModelScope.launch {
            val deviceEntity = devicesRepository.getDevice(pkDevice)
            val deviceDetailsState = DeviceDetailsState(
                icon = getPlatformIcon(deviceEntity?.platform),
                sn = deviceEntity?.pkDevice,
                macAddress = deviceEntity?.macAddress,
                firmware = deviceEntity?.firmware,
                model = deviceEntity?.platform
            )
            _uiState.update { it.copy(title = deviceEntity?.title, device = deviceDetailsState) }
        }
    }

    fun save(newTitle: String) {
        if (newTitle.isBlank()) {
            _uiState.update { it.copy(titleError = R.string.empty_field) }
            return
        }

        viewModelScope.launch {
            devicesRepository.updateTitle(pkDevice, newTitle)
            _uiState.update { it.copy(isSaved = true) }
        }
    }

    fun onSaved() {
        _uiState.update { it.copy(isSaved = false) }
    }

    fun onTitleChanged(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

}

data class DeviceDetailsUiState(
    val isEditMode: Boolean = false,
    val profile: ProfileState = myProfileState,
    var title: String? = null,
    @StringRes val titleError: Int? = null,
    val device: DeviceDetailsState? = null,
    val isSaved: Boolean = false
)

data class DeviceDetailsState(
    @DrawableRes val icon: Int,
    val sn: Int? = null,
    val macAddress: String? = null,
    val firmware: String? = null,
    val model: String? = null
)