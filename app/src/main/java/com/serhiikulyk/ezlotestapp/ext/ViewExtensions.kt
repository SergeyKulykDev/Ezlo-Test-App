package com.serhiikulyk.ezlotestapp.ext

import android.view.View

fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}