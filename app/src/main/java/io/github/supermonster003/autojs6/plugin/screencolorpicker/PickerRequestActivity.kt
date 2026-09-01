package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionConfig
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates

/** Visible user-consent bridge used by both the launcher and the host PendingIntent. */
class PickerRequestActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var overlaySettingsLaunched = false
    private var overlayExplanationVisible = false
    private var initialAdvanceDone = false
    private var notificationRequestLaunched = false
    private var projectionRequestLaunched = false
    private var startDispatched = false
    private var ownsStart = false
    private var completed = false

    private val stateListener: (Int) -> Unit = { state -> handler.post { onRuntimeState(state) } }
    private val startTimeout = Runnable {
        if (!completed && startDispatched && PickerRuntime.state() != ScreenColorPickerStates.ACTIVE) {
            ProjectionForegroundService.requestStop(this)
            finishFailure(R.string.text_color_picker_start_failed)
        }
    }

    private val projectionConsent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        projectionRequestLaunched = false
        val data = result.data
        if (result.resultCode != Activity.RESULT_OK || data == null) {
            finishFailure(R.string.text_screen_capture_permission_denied)
            return@registerForActivityResult
        }
        when (PickerRuntime.state()) {
            ScreenColorPickerStates.ACTIVE -> finishSuccess()
            ScreenColorPickerStates.STARTING -> {
                if (!ownsStart) {
                    waitForService()
                    return@registerForActivityResult
                }
                try {
                    val service = ProjectionForegroundService.startIntent(this, result.resultCode, data)
                    ContextCompat.startForegroundService(this, service)
                    startDispatched = true
                    waitForService()
                } catch (_: RuntimeException) {
                    PickerRuntime.markInactive()
                    finishFailure(R.string.text_color_picker_start_failed)
                }
            }
            ScreenColorPickerStates.INACTIVE -> finishCanceled()
            else -> finishFailure(R.string.text_color_picker_start_failed)
        }
    }

    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        notificationRequestLaunched = false
        if (!granted) {
            Toast.makeText(this, R.string.text_notification_permission_denied, Toast.LENGTH_LONG).show()
        }
        requestProjectionConsent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val restored = PickerRequestRestoreState(
            overlaySettingsLaunched = savedInstanceState?.getBoolean(STATE_OVERLAY_SETTINGS) == true,
            initialAdvanceDone = savedInstanceState?.getBoolean(STATE_ADVANCED) == true,
            notificationRequestLaunched = savedInstanceState?.getBoolean(STATE_NOTIFICATION_REQUEST) == true,
            projectionRequestLaunched = savedInstanceState?.getBoolean(STATE_PROJECTION_REQUEST) == true,
            startDispatched = savedInstanceState?.getBoolean(STATE_START_DISPATCHED) == true,
            ownsStart = savedInstanceState?.getBoolean(STATE_OWNS_START) == true,
        ).restoredForProcess(
            savedProcessInstanceId = savedInstanceState?.getString(STATE_PROCESS_INSTANCE_ID),
            currentProcessInstanceId = PickerRuntime.processInstanceId(),
        )
        overlaySettingsLaunched = restored.overlaySettingsLaunched
        initialAdvanceDone = restored.initialAdvanceDone
        notificationRequestLaunched = restored.notificationRequestLaunched
        projectionRequestLaunched = restored.projectionRequestLaunched
        startDispatched = restored.startDispatched
        ownsStart = restored.ownsStart
        when (PickerRuntime.state()) {
            ScreenColorPickerStates.ACTIVE -> finishSuccess()
            ScreenColorPickerStates.STOPPING -> finishFailure(R.string.text_color_picker_start_failed)
            ScreenColorPickerStates.INACTIVE -> {
                ownsStart = PickerRuntime.beginStart()
                if (!ownsStart) finishFailure(R.string.text_color_picker_start_failed)
            }
        }
        if (!completed) {
            watchRuntime()
            if (!ownsStart || startDispatched) waitForService()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        if (completed || isFinishing) return
        if (PickerRuntime.state() != ScreenColorPickerStates.STARTING) {
            if (PickerRuntime.state() == ScreenColorPickerStates.ACTIVE) finishSuccess() else finishCanceled()
            return
        }
        if (!ownsStart || startDispatched) {
            waitForService()
            return
        }
        if (overlaySettingsLaunched) {
            overlaySettingsLaunched = false
            if (Settings.canDrawOverlays(this)) requestNotificationThenProjection()
            else finishFailure(R.string.text_overlay_permission_required)
            return
        }
        if (!initialAdvanceDone) {
            if (Settings.canDrawOverlays(this)) {
                initialAdvanceDone = true
                requestNotificationThenProjection()
            } else {
                explainOverlayPermission()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(STATE_OVERLAY_SETTINGS, overlaySettingsLaunched)
        outState.putBoolean(STATE_ADVANCED, initialAdvanceDone)
        outState.putBoolean(STATE_NOTIFICATION_REQUEST, notificationRequestLaunched)
        outState.putBoolean(STATE_PROJECTION_REQUEST, projectionRequestLaunched)
        outState.putBoolean(STATE_START_DISPATCHED, startDispatched)
        outState.putBoolean(STATE_OWNS_START, ownsStart)
        outState.putString(STATE_PROCESS_INSTANCE_ID, PickerRuntime.processInstanceId())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        PickerRuntime.removeListener(stateListener)
        handler.removeCallbacks(startTimeout)
        if (isFinishing && !completed && ownsStart && PickerRuntime.state() == ScreenColorPickerStates.STARTING) {
            ProjectionForegroundService.requestStop(this)
        }
        super.onDestroy()
    }

    private fun explainOverlayPermission() {
        if (overlayExplanationVisible) return
        overlayExplanationVisible = true
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.text_overlay_permission_required)
            .setMessage(R.string.text_overlay_permission_explanation)
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                overlayExplanationVisible = false
                finishCanceled()
            }
            .setPositiveButton(android.R.string.ok) { _, _ ->
                overlayExplanationVisible = false
                initialAdvanceDone = true
                openOverlaySettings()
            }
            .setOnCancelListener { finishCanceled() }
            .setOnDismissListener { overlayExplanationVisible = false }
            .show()
    }

    private fun openOverlaySettings() {
        overlaySettingsLaunched = true
        val appSettings = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName"),
        )
        try {
            startActivity(appSettings)
        } catch (_: RuntimeException) {
            runCatching { startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)) }
                .onFailure { finishFailure(R.string.text_overlay_permission_required) }
        }
    }

    private fun requestProjectionConsent() {
        if (projectionRequestLaunched || completed) return
        if (!ownsStart || PickerRuntime.state() != ScreenColorPickerStates.STARTING) {
            finishCanceled()
            return
        }
        projectionRequestLaunched = true
        val manager = getSystemService(MediaProjectionManager::class.java)
        val intent = if (Build.VERSION.SDK_INT >= 34) {
            manager.createScreenCaptureIntent(MediaProjectionConfig.createConfigForDefaultDisplay())
        } else {
            manager.createScreenCaptureIntent()
        }
        projectionConsent.launch(intent)
    }

    private fun requestNotificationThenProjection() {
        if (Build.VERSION.SDK_INT < 33 ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        ) {
            requestProjectionConsent()
            return
        }
        if (!notificationRequestLaunched) {
            notificationRequestLaunched = true
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun watchRuntime() {
        PickerRuntime.removeListener(stateListener)
        PickerRuntime.addListener(stateListener)
    }

    private fun waitForService() {
        watchRuntime()
        handler.removeCallbacks(startTimeout)
        if (startDispatched) handler.postDelayed(startTimeout, START_TIMEOUT_MILLIS)
    }

    private fun onRuntimeState(state: Int) {
        when (state) {
            ScreenColorPickerStates.ACTIVE -> finishSuccess()
            ScreenColorPickerStates.INACTIVE -> finishCanceled()
        }
    }

    private fun finishSuccess() {
        if (completed) return
        completed = true
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun finishCanceled() {
        if (completed) return
        completed = true
        if (ownsStart && PickerRuntime.state() == ScreenColorPickerStates.STARTING) {
            ProjectionForegroundService.requestStop(this)
        }
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun finishFailure(message: Int) {
        if (completed) return
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finishCanceled()
    }

    private companion object {
        const val START_TIMEOUT_MILLIS = 12_000L
        const val STATE_OVERLAY_SETTINGS = "overlay_settings"
        const val STATE_ADVANCED = "advanced"
        const val STATE_NOTIFICATION_REQUEST = "notification_request"
        const val STATE_PROJECTION_REQUEST = "projection_request"
        const val STATE_START_DISPATCHED = "start_dispatched"
        const val STATE_OWNS_START = "owns_start"
        const val STATE_PROCESS_INSTANCE_ID = "process_instance_id"
    }
}
