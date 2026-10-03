package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

internal fun AppSettingsActivity.showDesignReference(): AlertDialog = MaterialAlertDialogBuilder(this)
    .setTitle(R.string.design_reference)
    .setMessage(R.string.design_reference_message)
    .setPositiveButton(android.R.string.ok, null)
    .setNeutralButton(R.string.design_reference_documents) { _, _ ->
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://github.com/SuperMonster003/AutoJs6-Plugin-Three-Color-Picker/blob/master/docs/design-reference.md",
            )))
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.design_reference_open_failed, Toast.LENGTH_LONG).show()
        }
    }
    .show().also { SettingsUi(this, settingsPalette).styleDialog(it) }
