package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.app.Activity
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.View
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.autojs.plugin.screencolorpicker.api.IScreenColorPickerPlugin
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerContract
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.sameInstance
import java.nio.ByteBuffer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.cos
import kotlin.math.sin

@RunWith(AndroidJUnit4::class)
class ScreenColorPickerPluginInstrumentedTest {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentation.targetContext
    private var connection: ServiceConnection? = null

    @After
    fun unbind() {
        connection?.let { runCatching { context.unbindService(it) } }
        connection = null
    }

    @Test
    fun serviceIsDiscoveredByExactSharedActionAndCategory() {
        val intent = Intent(ScreenColorPickerContract.SERVICE_ACTION)
            .addCategory(ScreenColorPickerContract.SERVICE_CATEGORY)
            .setPackage(context.packageName)
        val matches = context.packageManager.queryIntentServicesCompat(intent)
        assertEquals(1, matches.size)
        assertEquals(ScreenColorPickerPluginService::class.java.name, matches.single().serviceInfo.name)
        assertEquals(ScreenColorPickerContract.PLUGIN_PERMISSION, matches.single().serviceInfo.permission)
        assertTrue(matches.single().serviceInfo.exported)
    }

    @Test
    fun binderDescriptorInfoStatePendingIntentAndRepeatedStopMatchContract() {
        val (rawBinder, api) = bindPlugin()
        assertEquals(IScreenColorPickerPlugin.DESCRIPTOR, rawBinder.interfaceDescriptor)

        val info = api.info
        val installed = context.packageManager.packageInfo().let {
            if (Build.VERSION.SDK_INT >= 28) it.longVersionCode else {
                @Suppress("DEPRECATION")
                it.versionCode.toLong()
            }
        }
        assertEquals(context.getString(R.string.app_name), info.name)
        assertEquals(context.getString(R.string.plugin_description), info.description)
        assertEquals(PLUGIN_INSTRUCTION_REFERENCE, info.instruction)
        assertEquals(context.getString(R.string.plugin_author), info.author)
        assertEquals(context.packageManager.packageInfo().versionName, info.versionName)
        assertEquals(installed, info.versionCode)
        assertEquals(ScreenColorPickerContract.PLUGIN_ID, info.id)
        assertEquals(ScreenColorPickerContract.ENGINE, info.engine)
        assertEquals(ScreenColorPickerContract.VARIANT_DEFAULT, info.variant)
        assertTrue(info.collaborators.orEmpty().isEmpty())
        assertTrue(info.supportedAbis.orEmpty().isEmpty())
        val capabilities = requireNotNull(info.capabilities)
        assertEquals(
            ScreenColorPickerContract.VERSION,
            capabilities.getInt(ScreenColorPickerContract.CAPABILITY_CONTRACT_VERSION),
        )
        assertEquals(
            ScreenColorPickerContract.REQUIRED_HOST_VERSION_CODE,
            capabilities.getLong(ScreenColorPickerContract.CAPABILITY_REQUIRES_HOST_VERSION),
        )
        assertTrue(ScreenColorPickerStates.isKnown(api.state))

        val pendingIntent = api.startPendingIntent
        assertNotNull(pendingIntent)
        assertEquals(context.packageName, pendingIntent.creatorPackage)
        if (Build.VERSION.SDK_INT >= 31) {
            assertTrue(pendingIntent.isImmutable)
            assertTrue(pendingIntent.isActivity)
        }

        api.stop()
        waitUntilInactive(api)
        assertEquals(ScreenColorPickerStates.INACTIVE, api.state)
        assertFalse(api.stop())
    }

    @Test
    fun startPendingIntentLaunchesRequestActivityAndReturnsResultToCaller() {
        val (_, api) = bindPlugin()
        val pendingIntent = requireNotNull(api.startPendingIntent)
        val requestMonitor = instrumentation.addMonitor(PickerRequestActivity::class.java.name, null, false)
        val resultCode = AtomicInteger(Int.MIN_VALUE)
        val resultLatch = CountDownLatch(1)
        api.stop()
        waitUntilInactive(api)
        PickerRuntime.markInactive()
        assertTrue(PickerRuntime.beginStart())
        assertTrue(PickerRuntime.beginStop())

        try {
            ActivityScenario.launch<MainActivity>(
                Intent(context, MainActivity::class.java),
            ).use { scenario ->
                scenario.onActivity { activity ->
                    val resultFragment = IntentSenderResultFragment().apply {
                        onResult = { result ->
                            resultCode.set(result)
                            resultLatch.countDown()
                        }
                    }
                    activity.supportFragmentManager.beginTransaction()
                        .add(resultFragment, INTENT_SENDER_FRAGMENT_TAG)
                        .commitNow()
                    resultFragment.launch(pendingIntent)
                }

                val requestActivity = instrumentation.waitForMonitorWithTimeout(requestMonitor, 5_000)
                assertNotNull("PendingIntent did not launch PickerRequestActivity", requestActivity)
                assertTrue("IntentSender result was not delivered", resultLatch.await(5, TimeUnit.SECONDS))
                assertEquals(Activity.RESULT_CANCELED, resultCode.get())
            }
        } finally {
            instrumentation.removeMonitor(requestMonitor)
            PickerRuntime.markInactive()
        }
    }

    @Test
    fun rowPaddedRgbaBufferConvertsDirectlyToFinalBitmapColors() {
        val layout = RgbaPlaneLayout(width = 2, height = 2, pixelStride = 4, rowStride = 12)
        val buffer = ByteBuffer.wrap(
            byteArrayOf(
                0x11, 0x22, 0x33, 0xff.toByte(),
                0x55, 0x66, 0x77, 0xff.toByte(),
                0x01, 0x02, 0x03, 0x04,
                0x80.toByte(), 0x90.toByte(), 0xa0.toByte(), 0xff.toByte(),
                0xc0.toByte(), 0xd0.toByte(), 0xe0.toByte(), 0xff.toByte(),
            ),
        )

        val bitmap = ImagePlaneBitmapConverter.convert(buffer, layout)
        try {
            assertEquals(2, bitmap.width)
            assertEquals(2, bitmap.height)
            assertEquals(Color.rgb(0x11, 0x22, 0x33), bitmap.getPixel(0, 0))
            assertEquals(Color.rgb(0x55, 0x66, 0x77), bitmap.getPixel(1, 0))
            assertEquals(Color.rgb(0x80, 0x90, 0xa0), bitmap.getPixel(0, 1))
            assertEquals(Color.rgb(0xc0, 0xd0, 0xe0), bitmap.getPixel(1, 1))
        } finally {
            bitmap.recycle()
        }
    }

    @Test
    fun magnifierViewBuildsFromApplicationContextWithMtTextsAndHitZones() {
        val cells = PickerSettingsCatalog.gridCellCountFor(8)
        val size = 500
        lateinit var magnifier: MagnifierView
        instrumentation.runOnMainSync {
            magnifier = MagnifierView(
                requireNotNull(context.applicationContext),
                gridCells = cells,
                showGrid = true,
                hexFormat = true,
            )
            magnifier.measure(
                View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY),
            )
            magnifier.layout(0, 0, size, size)
            magnifier.updateSample(IntArray(cells * cells) { Color.rgb(0xFA, 0xFA, 0xFA) }, 342, 1951)
        }

        assertEquals(Color.rgb(0xFA, 0xFA, 0xFA), magnifier.centerColor)
        assertEquals("#FAFAFA", magnifier.colorCopyText(numericOnly = false))
        assertEquals("FAFAFA", magnifier.colorCopyText(numericOnly = true))
        assertEquals("343,1952", magnifier.coordinateText())
        instrumentation.runOnMainSync { magnifier.hexFormat = false }
        assertEquals("R250,G250,B250", magnifier.colorCopyText(numericOnly = false))
        assertEquals("250,250,250", magnifier.colorCopyText(numericOnly = true))

        val center = size / 2f
        val bandRadius = size / 2f - 1f
        fun pointAt(angleFromNoonDegrees: Double): Pair<Float, Float> {
            val radians = Math.toRadians(angleFromNoonDegrees)
            return (center + bandRadius * sin(radians)).toFloat() to
                (center - bandRadius * cos(radians)).toFloat()
        }

        val colorPoint = pointAt(-40.0)
        val coordinatePoint = pointAt(40.0)
        val closePoint = pointAt(180.0)
        assertEquals(MagnifierHitZone.COLOR_TEXT, magnifier.hitZone(colorPoint.first, colorPoint.second))
        assertEquals(MagnifierHitZone.COORDINATE_TEXT, magnifier.hitZone(coordinatePoint.first, coordinatePoint.second))
        assertEquals(MagnifierHitZone.CLOSE, magnifier.hitZone(closePoint.first, closePoint.second))
        assertEquals(MagnifierHitZone.NONE, magnifier.hitZone(center, center))
    }

    @Test
    fun manifestKeepsLauncherWakeAndCaptureComponentsAtRequiredBoundaries() {
        val packageInfo = context.packageManager.packageInfo()
        val activities = packageInfo.activities.orEmpty().associateBy(ActivityInfo::name)
        val services = packageInfo.services.orEmpty().associateBy(ServiceInfo::name)

        val launcher = activities.getValue(MainActivity::class.java.name)
        assertTrue(launcher.exported)
        assertNull(launcher.permission)
        val launcherMatches = context.packageManager.queryIntentActivitiesCompat(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER).setPackage(context.packageName),
        )
        assertTrue(launcherMatches.any { it.activityInfo.name == MainActivity::class.java.name })

        val request = activities.getValue(PickerRequestActivity::class.java.name)
        assertFalse(request.exported)
        val wake = activities.getValue(WakeActivity::class.java.name)
        assertTrue(wake.exported)
        assertEquals(ScreenColorPickerContract.PLUGIN_PERMISSION, wake.permission)
        val wakeMatches = context.packageManager.queryIntentActivitiesCompat(
            Intent("org.autojs.plugin.action.WAKE")
                .addCategory(Intent.CATEGORY_DEFAULT)
                .setPackage(context.packageName),
        )
        assertTrue(wakeMatches.any { it.activityInfo.name == WakeActivity::class.java.name })
        assertEquals(
            ".WakeActivity",
            packageInfo.applicationInfo?.metaData?.getString("org.autojs.plugin.WAKE_ACTIVITY"),
        )

        val binder = services.getValue(ScreenColorPickerPluginService::class.java.name)
        assertTrue(binder.exported)
        assertEquals(ScreenColorPickerContract.PLUGIN_PERMISSION, binder.permission)
        val projection = services.getValue(ProjectionForegroundService::class.java.name)
        assertFalse(projection.exported)
        if (Build.VERSION.SDK_INT >= 29) {
            assertTrue(
                projection.foregroundServiceType and ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION != 0,
            )
        }

        val permissions = packageInfo.requestedPermissions.orEmpty().toSet()
        assertTrue(android.Manifest.permission.SYSTEM_ALERT_WINDOW in permissions)
        assertTrue(android.Manifest.permission.FOREGROUND_SERVICE in permissions)
        assertTrue("android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION" in permissions)
        assertTrue(android.Manifest.permission.POST_NOTIFICATIONS in permissions)
        assertTrue(ScreenColorPickerContract.PLUGIN_PERMISSION in permissions)
    }

    @Test
    fun launcherStartsWithoutAnyHostComponent() {
        val activity = instrumentation.startActivitySync(
            Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
        )
        assertEquals(MainActivity::class.java, activity.javaClass)
        instrumentation.runOnMainSync { activity.finish() }
    }

    @Test
    fun overlayExplanationSurvivesActivityRecreationAndCancelRestoresInactiveState() {
        // A pre-authorized developer device cannot exercise the explanation dialog without
        // mutating user settings. Fresh CI emulators take the full regression path.
        assumeFalse(Settings.canDrawOverlays(context))
        PickerRuntime.markInactive()

        ActivityScenario.launch<PickerRequestActivity>(
            Intent(context, PickerRequestActivity::class.java),
        ).use { scenario ->
            lateinit var oldDialogDecor: View
            onView(withText(R.string.text_overlay_permission_explanation))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .check { view, error ->
                    if (error != null) throw error
                    oldDialogDecor = requireNotNull(view).rootView
                }

            scenario.recreate()

            instrumentation.runOnMainSync {
                assertFalse("Recreation must detach the old dialog window", oldDialogDecor.isAttachedToWindow)
            }
            val recreatedDialog = allOf(isDialog(), withDecorView(not(sameInstance(oldDialogDecor))))
            onView(withText(R.string.text_overlay_permission_explanation))
                .inRoot(recreatedDialog)
                .check(matches(isDisplayed()))
            onView(withText(android.R.string.cancel)).inRoot(recreatedDialog).perform(click())
        }

        assertEquals(ScreenColorPickerStates.INACTIVE, PickerRuntime.state())
    }

    private fun bindPlugin(): Pair<IBinder, IScreenColorPickerPlugin> {
        val latch = CountDownLatch(1)
        var rawBinder: IBinder? = null
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                rawBinder = service
                latch.countDown()
            }

            override fun onServiceDisconnected(name: ComponentName) = Unit
        }
        connection = serviceConnection
        val intent = Intent(context, ScreenColorPickerPluginService::class.java)
        assertTrue(context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE))
        assertTrue("Timed out binding plugin service", latch.await(5, TimeUnit.SECONDS))
        val binder = requireNotNull(rawBinder)
        return binder to IScreenColorPickerPlugin.Stub.asInterface(binder)
    }

    private fun waitUntilInactive(api: IScreenColorPickerPlugin) {
        val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3)
        while (api.state != ScreenColorPickerStates.INACTIVE && System.nanoTime() < deadline) {
            Thread.sleep(25)
        }
    }

    private fun PackageManager.packageInfo(): PackageInfo {
        val flags = PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES or
            PackageManager.GET_PERMISSIONS or PackageManager.GET_META_DATA
        return if (Build.VERSION.SDK_INT >= 33) {
            getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
        } else {
            @Suppress("DEPRECATION")
            getPackageInfo(context.packageName, flags)
        }
    }

    private fun PackageManager.queryIntentServicesCompat(intent: Intent) = if (Build.VERSION.SDK_INT >= 33) {
        queryIntentServices(intent, PackageManager.ResolveInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION")
        queryIntentServices(intent, 0)
    }

    private fun PackageManager.queryIntentActivitiesCompat(intent: Intent) = if (Build.VERSION.SDK_INT >= 33) {
        queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION")
        queryIntentActivities(intent, 0)
    }

    private companion object {
        const val INTENT_SENDER_FRAGMENT_TAG = "intent_sender_result"
    }
}

class IntentSenderResultFragment : Fragment() {
    var onResult: (Int) -> Unit = {}

    private val launcher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        onResult(result.resultCode)
    }

    fun launch(pendingIntent: PendingIntent) {
        launcher.launch(IntentSenderRequest.Builder(pendingIntent.intentSender).build())
    }
}
