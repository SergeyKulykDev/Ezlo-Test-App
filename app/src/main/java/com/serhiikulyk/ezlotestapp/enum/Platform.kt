package com.serhiikulyk.ezlotestapp.enum

import androidx.annotation.DrawableRes
import com.serhiikulyk.ezlotestapp.R

enum class Platform(val key: String) {
    Sercomm_G450("Sercomm_G450"),
    Sercomm_G550("Sercomm_G550"),
    MiCasaVerde_VeraLite("MiCasaVerde_VeraLite"),
    Sercomm_NA900("Sercomm_NA900"),
    Sercomm_NA301("Sercomm_NA301"),
    Sercomm_NA930("Sercomm_NA930");
}

// TASK 3: The item icon is displayed depending on "Platform" key
@DrawableRes
fun getPlatformIcon(byKey: String?): Int = when (byKey) {
    Platform.Sercomm_G450.key -> R.drawable.vera_plus_big
    Platform.Sercomm_G550.key -> R.drawable.vera_secure_big
    Platform.MiCasaVerde_VeraLite.key,
    Platform.Sercomm_NA900.key,
    Platform.Sercomm_NA301.key,
    Platform.Sercomm_NA930.key -> R.drawable.vera_edge_big

    else -> R.drawable.vera_edge_big
}

