package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin

class ThreeColorPickerPluginService : Service() {
    private val binder = object : IScreenColorPickerPlugin.Stub() {
        override fun getInfo(): PluginInfo = PluginRuntimeInfo.create(this@ThreeColorPickerPluginService).apply { supportedAbis = emptyArray() }

        override fun getState(): Int = PickerRuntime.state()

        override fun getStartPendingIntent(): PendingIntent {
            // The host launches this IntentSender for a result. Task flags would detach the
            // request Activity from its caller on Android 16 and prevent result delivery.
            val intent = Intent(this@ThreeColorPickerPluginService, PickerRequestActivity::class.java)
            return PendingIntent.getActivity(
                this@ThreeColorPickerPluginService,
                REQUEST_START_PICKER,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }

        override fun stop(): Boolean = ProjectionForegroundService.requestStop(
            this@ThreeColorPickerPluginService,
        )
    }

    override fun onBind(intent: Intent?): IBinder = binder

    private companion object {
        const val REQUEST_START_PICKER = 0x534350
    }
}
