package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.res.Configuration
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates

class ProjectionForegroundService : Service() {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var session: ProjectionSession? = null
    private var closingSession: ProjectionSession? = null
    private var overlay: PickerOverlayController? = null
    private var foregroundStarted = false
    private var shuttingDown = false
    private var shutdownFinalized = false
    private var stopSelfAfterShutdown = false

    override fun onCreate() {
        super.onCreate()
        activeInstance = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (shuttingDown) {
            rejectLateStart(intent, startId)
            return START_NOT_STICKY
        }
        when (intent?.action) {
            ACTION_STOP -> stopFromServiceAction()
            ACTION_START -> startProjection(intent)
            else -> stopAfterUnexpectedStart()
        }
        return START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        overlay?.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        shutdown(stopProjection = true, stopSelfAfter = false)
        if (activeInstance === this) activeInstance = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startProjection(intent: Intent) {
        try {
            ensureForeground()
        } catch (_: RuntimeException) {
            failStart()
            return
        }
        if (session != null) return
        if (PickerRuntime.state() != ScreenColorPickerStates.STARTING) {
            shutdown(stopProjection = true, stopSelfAfter = true)
            return
        }
        val resultData = intent.projectionResultData()
        val resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, Int.MIN_VALUE)
        if (resultData == null || resultCode == Int.MIN_VALUE) {
            failStart()
            return
        }
        try {
            val manager = getSystemService(MediaProjectionManager::class.java)
            val projection = manager.getMediaProjection(resultCode, resultData)
                ?: error("MediaProjection permission result was rejected")
            val newSession = ProjectionSession(this, projection) {
                mainHandler.post { shutdown(stopProjection = false, stopSelfAfter = true) }
            }
            session = newSession
            val newOverlay = PickerOverlayController(this, newSession) {
                mainHandler.post { requestStop(this) }
            }
            overlay = newOverlay
            newOverlay.showBubble()
            if (!PickerRuntime.markActive()) {
                shutdown(stopProjection = true, stopSelfAfter = true)
            }
        } catch (_: RuntimeException) {
            failStart()
        }
    }

    private fun failStart() {
        Toast.makeText(this, R.string.text_color_picker_start_failed, Toast.LENGTH_LONG).show()
        shutdown(stopProjection = true, stopSelfAfter = true)
    }

    private fun stopFromServiceAction() {
        PickerRuntime.beginStop()
        shutdown(stopProjection = true, stopSelfAfter = true)
    }

    private fun stopAfterUnexpectedStart() {
        ensureForeground()
        shutdown(stopProjection = true, stopSelfAfter = true)
    }

    private fun shutdown(stopProjection: Boolean, stopSelfAfter: Boolean) {
        stopSelfAfterShutdown = stopSelfAfterShutdown || stopSelfAfter
        if (shuttingDown) {
            if (stopProjection) closingSession?.close(stopProjection = true)
            return
        }
        shuttingDown = true
        PickerRuntime.beginStop()
        overlay?.close()
        overlay = null
        val sessionToClose = session
        session = null
        closingSession = sessionToClose
        if (sessionToClose == null) {
            finishShutdown()
        } else {
            sessionToClose.close(stopProjection) {
                if (closingSession === sessionToClose) closingSession = null
                finishShutdown()
            }
        }
    }

    private fun finishShutdown() {
        if (shutdownFinalized) return
        shutdownFinalized = true
        removeForeground()
        if (activeInstance === this) activeInstance = null
        PickerRuntime.markInactive()
        if (stopSelfAfterShutdown) stopSelf()
    }

    private fun rejectLateStart(intent: Intent?, startId: Int) {
        stopSelfAfterShutdown = true
        if (!shutdownFinalized) return
        // A late startForegroundService delivery still needs its foreground deadline satisfied.
        if (intent?.action == ACTION_START && !foregroundStarted) {
            runCatching { ensureForeground() }
        }
        removeForeground()
        stopSelfResult(startId)
    }

    private fun removeForeground() {
        if (foregroundStarted) {
            @Suppress("DEPRECATION")
            stopForeground(STOP_FOREGROUND_REMOVE)
            foregroundStarted = false
        }
    }

    private fun ensureForeground() {
        if (foregroundStarted) return
        val notificationManager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    getString(R.string.notification_channel_screen_color_picker),
                    NotificationManager.IMPORTANCE_LOW,
                ).apply {
                    setShowBadge(false)
                    description = getString(R.string.notification_text_screen_color_picker)
                },
            )
        }
        val notification = buildNotification()
        if (Build.VERSION.SDK_INT >= 29) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION,
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
        foregroundStarted = true
    }

    private fun buildNotification(): Notification {
        val openApp = PendingIntent.getActivity(
            this,
            REQUEST_OPEN_APP,
            Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val stop = PendingIntent.getService(
            this,
            REQUEST_STOP,
            Intent(this, ProjectionForegroundService::class.java).setAction(ACTION_STOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_notification_color_picker)
            .setContentTitle(getString(R.string.notification_title_screen_color_picker))
            .setContentText(getString(R.string.notification_text_screen_color_picker))
            .setContentIntent(openApp)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .addAction(0, getString(R.string.notification_action_stop), stop)
            .build()
    }

    private fun Intent.projectionResultData(): Intent? = if (Build.VERSION.SDK_INT >= 33) {
        getParcelableExtra(EXTRA_RESULT_DATA, Intent::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(EXTRA_RESULT_DATA)
    }

    companion object {
        private const val ACTION_START = "io.github.supermonster003.autojs6.plugin.screencolorpicker.action.START"
        private const val ACTION_STOP = "io.github.supermonster003.autojs6.plugin.screencolorpicker.action.STOP"
        private const val EXTRA_RESULT_CODE = "projection_result_code"
        private const val EXTRA_RESULT_DATA = "projection_result_data"
        private const val NOTIFICATION_CHANNEL = "screen_color_picker"
        private const val NOTIFICATION_ID = 0x534350
        private const val REQUEST_OPEN_APP = 0x534351
        private const val REQUEST_STOP = 0x534352

        @Volatile
        private var activeInstance: ProjectionForegroundService? = null

        fun startIntent(context: Context, resultCode: Int, resultData: Intent): Intent =
            Intent(context, ProjectionForegroundService::class.java)
                .setAction(ACTION_START)
                .putExtra(EXTRA_RESULT_CODE, resultCode)
                .putExtra(EXTRA_RESULT_DATA, resultData)

        /** Returns true only for the first accepted stop transition. */
        fun requestStop(@Suppress("UNUSED_PARAMETER") context: Context): Boolean {
            if (!PickerRuntime.beginStop()) return false
            val service = activeInstance
            if (service == null) {
                PickerRuntime.markInactive()
            } else {
                service.mainHandler.post { service.shutdown(stopProjection = true, stopSelfAfter = true) }
            }
            return true
        }
    }
}
