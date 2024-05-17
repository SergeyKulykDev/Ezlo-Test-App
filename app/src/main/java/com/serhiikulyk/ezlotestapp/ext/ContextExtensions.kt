package com.serhiikulyk.ezlotestapp.ext

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

fun Context.showMaterialAlertDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            onPositiveButtonClick()
            dialog.dismiss()
        }
        .setNegativeButton(negativeButtonText) { dialog, _ ->
            onNegativeButtonClick()
            dialog.dismiss()
        }
        .show()
}


class Prefs @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.defaultSharedPreferences: SharedPreferences
        get() = this.getSharedPreferences("${this.packageName}_preferences", Context.MODE_PRIVATE)


    var isSynced: Boolean
        set(value) {
            context.defaultSharedPreferences.edit { putBoolean("is_synced", value) }
        }
        get() {
            return context.defaultSharedPreferences.getBoolean("is_synced", false)
        }

}