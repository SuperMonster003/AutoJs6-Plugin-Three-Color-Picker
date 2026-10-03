package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.autojs.plugin.common.api.IPluginInfoProvider
import org.autojs.plugin.common.api.PluginInfo

/** Read-only official discovery; does not start capture or change the screen-picker wire protocol. */
class ThreeColorPickerInfoService : Service() {
    private val binder = object : IPluginInfoProvider.Stub() {
        override fun getInfo(): PluginInfo = PluginRuntimeInfo.create(this@ThreeColorPickerInfoService).apply {
            supportedAbis = emptyArray()
        }
    }

    override fun onBind(intent: Intent?): IBinder = binder
}
