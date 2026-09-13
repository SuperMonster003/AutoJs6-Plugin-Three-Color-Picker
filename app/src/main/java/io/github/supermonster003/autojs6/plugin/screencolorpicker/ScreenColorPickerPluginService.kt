package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin

class ScreenColorPickerPluginService : Service() {
    private val binder = object : IScreenColorPickerPlugin.Stub() {
        override fun getInfo(): PluginInfo = PluginRuntimeInfo.create(this@ScreenColorPickerPluginService).apply { supportedAbis = emptyArray() }

        override fun getState(): Int = PickerRuntime.state()

        override fun getStartPendingIntent(): PendingIntent {
            // The host launches this IntentSender for a result. Task flags would detach the
            // request Activity from its caller on Android 16 and prevent result delivery.
            val intent = Intent(this@ScreenColorPickerPluginService, PickerRequestActivity::class.java)
            return PendingIntent.getActivity(
                this@ScreenColorPickerPluginService,
                REQUEST_START_PICKER,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }

        override fun stop(): Boolean = ProjectionForegroundService.requestStop(
            this@ScreenColorPickerPluginService,
        )
    }

    override fun onBind(intent: Intent?): IBinder = binder

    private companion object {
        const val REQUEST_START_PICKER = 0x534350
    }
}
