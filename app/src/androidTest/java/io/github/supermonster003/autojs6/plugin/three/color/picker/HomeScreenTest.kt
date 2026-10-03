package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.os.SystemClock
import androidx.appcompat.app.AlertDialog
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.button.MaterialButton
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates
import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.Matchers.`is`
import java.io.File

class HomeScreenTest {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentation.targetContext

    @Test fun themedHomeShowsRealArtworkAndNeverStartsCaptureOnLaunch() {
        val previous = AppearanceChoice.read(context)
        try {
            for ((language, night) in listOf("zh-Hans" to "light", "zh-Hans" to "dark", "ar" to "light")) {
                previous.copy(language = language, night = night, color = AppearanceChoice.FALLBACK_COLOR).save(context)
                ActivityScenario.launch<MainActivity>(Intent(context, MainActivity::class.java)).use { scenario ->
                    instrumentation.waitForIdleSync()
                    onView(withTagValue(`is`("home-brand-icon" as Any))).check(matches(isDisplayed()))
                    scenario.onActivity { activity ->
                        val root = activity.findViewById<View>(android.R.id.content)
                        val icon = root.findViewWithTag<ImageView>("home-brand-icon")
                        assertTrue(icon.drawable is BitmapDrawable)
                        val action = root.findViewWithTag<MaterialButton>("picker-action")
                        assertEquals(activity.getString(R.string.text_start_color_picker), action.text.toString())
                        assertTrue(action.isEnabled)
                        assertEquals(activity.getString(R.string.home_ready), root.findViewWithTag<TextView>("picker-status").text.toString())
                        assertEquals(ScreenColorPickerStates.INACTIVE, PickerRuntime.state())
                        assertEquals(if (language == "ar") View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR, root.layoutDirection)
                        assertNotNull(root.findViewWithTag<View>("open-settings"))
                    }
                    if (InstrumentationRegistry.getArguments().getString("captureHome") == "true") {
                        // Wait for the window transition to reach the compositor before capturing evidence.
                        SystemClock.sleep(700)
                        instrumentation.waitForIdleSync()
                        val screenshot = instrumentation.uiAutomation.takeScreenshot()
                        val file = File(context.getExternalFilesDir(null), "home-$language-$night.png")
                        file.outputStream().use { screenshot.compress(Bitmap.CompressFormat.PNG, 100, it) }
                        screenshot.recycle()
                    }
                    scenario.recreate()
                    scenario.onActivity { assertEquals(ScreenColorPickerStates.INACTIVE, PickerRuntime.state()) }
                }
            }
        } finally { previous.save(context) }
    }

    @Test fun settingsExplainsDesignReferenceWithoutChangingAppearance() {
        val previous = AppearanceChoice.read(context)
        ActivityScenario.launch<AppSettingsActivity>(Intent(context, AppSettingsActivity::class.java)).use { scenario ->
            scenario.onActivity { activity ->
                activity.findViewById<View>(android.R.id.content).findViewWithTag<View>("design-reference").performClick()
                val dialog = activity.prompt!!
                assertTrue(dialog.findViewById<TextView>(android.R.id.message)!!.text.contains("bm.mt.plus"))
                assertTrue(dialog.findViewById<TextView>(android.R.id.message)!!.text.contains("v2.26.9"))
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick()
                assertEquals(previous, AppearanceChoice.read(activity))
            }
        }
    }
}
