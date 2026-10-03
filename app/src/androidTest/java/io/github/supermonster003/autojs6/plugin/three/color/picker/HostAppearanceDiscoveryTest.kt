package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.test.platform.app.InstrumentationRegistry
import org.autojs.plugin.common.api.IPluginInfoProvider
import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class HostAppearanceDiscoveryTest {
    @Test fun commonInfoEntryIsProtectedAndReturnsTheSamePickerMetadataWithoutStartingCapture() {
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        val matches=context.packageManager.queryIntentServices(Intent("org.autojs.plugin.INFO").addCategory("three-color-picker").setPackage(context.packageName),0)
        assertEquals(1,matches.size)
        val service=matches.single().serviceInfo
        assertEquals("org.autojs.permission.PLUGIN",service.permission)
        assertTrue(service.exported)
        val before=PickerRuntime.state()
        val latch=CountDownLatch(1)
        var binder: IBinder?=null
        val connection=object:ServiceConnection {
            override fun onServiceConnected(name: ComponentName?,value: IBinder?) { binder=value;latch.countDown() }
            override fun onServiceDisconnected(name: ComponentName?) = Unit
        }
        assertTrue(context.bindService(Intent(context,ThreeColorPickerInfoService::class.java),connection,Context.BIND_AUTO_CREATE))
        try {
            assertTrue(latch.await(5,TimeUnit.SECONDS))
            val info=IPluginInfoProvider.Stub.asInterface(binder).info
            val expected=PluginRuntimeInfo.create(context)
            assertEquals(expected.id,info.id);assertEquals(expected.engine,info.engine)
            assertEquals(expected.versionCode,info.versionCode);assertEquals(expected.versionName,info.versionName)
            assertEquals(before,PickerRuntime.state());assertNotNull(info.supportedAbis);assertTrue(info.supportedAbis!!.isEmpty())
        } finally { context.unbindService(connection) }
    }
}
