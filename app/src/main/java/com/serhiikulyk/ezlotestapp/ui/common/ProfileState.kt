package com.serhiikulyk.ezlotestapp.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.serhiikulyk.ezlotestapp.R

data class ProfileState(
    @DrawableRes val avatarRes: Int,
    @StringRes val fullNameRes: Int,
)

val myProfileState = ProfileState(avatarRes = R.drawable.avatar, fullNameRes = R.string.full_name)
