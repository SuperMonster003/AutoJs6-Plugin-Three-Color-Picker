package io.github.supermonster003.autojs6.plugin.three.color.picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates

class MainActivity : AppearanceActivity() {
    private lateinit var ui: SettingsUi
    private lateinit var statusText: TextView
    private lateinit var detailText: TextView
    private lateinit var statusMarker: View
    private lateinit var actionButton: MaterialButton

    private val startPicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        renderState(PickerRuntime.state())
        if (result.resultCode == Activity.RESULT_OK) moveTaskToBack(true)
    }
    private val stateListener: (Int) -> Unit = { state -> runOnUiThread { renderState(state) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = SettingsUi(this, settingsPalette)
        setContentView(buildContent())
    }

    override fun onStart() {
        super.onStart()
        PickerRuntime.addListener(stateListener)
        renderState(PickerRuntime.state())
    }

    override fun onStop() {
        PickerRuntime.removeListener(stateListener)
        super.onStop()
    }

    private fun buildContent(): View {
        val content = HomeColumn(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(ui.dp(24), ui.dp(24), ui.dp(24), ui.dp(24))
        }
        val hero = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
        }
        hero.addView(ImageView(this).apply {
            tag = "home-brand-icon"
            setImageResource(R.mipmap.ic_launcher)
            scaleType = ImageView.ScaleType.FIT_CENTER
            background = ui.fill(settingsPalette.background, 28, stroke = true)
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }, LinearLayout.LayoutParams(ui.dp(120), ui.dp(120)))
        hero.addView(ui.text(getString(R.string.app_name), 28f).apply {
            gravity = Gravity.CENTER
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            ViewCompat.setAccessibilityHeading(this, true)
        }, block(top = 20))
        hero.addView(ui.text(getString(R.string.text_color_picker_home_description), 16f, true).apply {
            gravity = Gravity.CENTER
            setLineSpacing(0f, 1.15f)
        }, block(top = 10))
        content.addView(hero, block())

        val session = LinearLayout(this).apply {
            tag = "picker-session"
            orientation = LinearLayout.VERTICAL
            background = ui.fill(settingsPalette.surface, 24)
            setPadding(ui.dp(20), ui.dp(20), ui.dp(20), ui.dp(20))
        }
        val statusRow = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        statusMarker = View(this).apply { importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO }
        statusRow.addView(statusMarker, LinearLayout.LayoutParams(ui.dp(8), ui.dp(8)).apply { marginEnd = ui.dp(10) })
        statusText = ui.text("", 16f).apply {
            tag = "picker-status"
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE
        }
        statusRow.addView(statusText, LinearLayout.LayoutParams(0, -2, 1f))
        session.addView(statusRow, block())
        detailText = ui.text("", 14f, true).apply { tag = "picker-status-detail"; setLineSpacing(0f, 1.15f) }
        session.addView(detailText, block(top = 10))
        actionButton = MaterialButton(this).apply {
            tag = "picker-action"
            isAllCaps = false
            minHeight = ui.dp(56)
            cornerRadius = ui.dp(16)
            setOnClickListener {
                when (PickerRuntime.state()) {
                    ScreenColorPickerStates.ACTIVE -> ProjectionForegroundService.requestStop(this@MainActivity)
                    ScreenColorPickerStates.INACTIVE -> startPicker.launch(Intent(this@MainActivity, PickerRequestActivity::class.java))
                }
            }
        }
        ui.tint(actionButton)
        session.addView(actionButton, block(top = 16))
        content.addView(session, block(top = 28))

        content.addView(ui.text(getString(R.string.home_quick_guide), 14f, true).apply {
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            ViewCompat.setAccessibilityHeading(this, true)
        }, block(top = 28))
        guide(content, 1, R.string.home_guide_position, R.string.text_picker_instruction)
        guide(content, 2, R.string.home_guide_copy, R.string.text_picker_instruction_copy)
        guide(content, 3, R.string.home_guide_stop, R.string.text_picker_instruction_close)
        content.addView(View(this).apply { setBackgroundColor(settingsPalette.divider) },
            LinearLayout.LayoutParams(-1, ui.dp(1)).apply { topMargin = ui.dp(20) })
        content.addView(ui.row(R.string.settings_title, getString(R.string.home_settings_summary),
            R.drawable.ic_settings_tune, "open-settings") {
            startActivity(Intent(this, AppSettingsActivity::class.java))
        }.apply { setPadding(0, ui.dp(12), 0, ui.dp(12)) }, block(top = 4))
        content.addView(ui.text(getString(R.string.home_privacy_note), 12f, true).apply {
            gravity = Gravity.CENTER
        }, block(top = 12))

        val frame = FrameLayout(this).apply {
            addView(content, FrameLayout.LayoutParams(-1, -2, Gravity.TOP or Gravity.CENTER_HORIZONTAL))
        }
        return ScrollView(this).apply {
            id = R.id.home_scroll
            isFillViewport = true
            setBackgroundColor(settingsPalette.background)
            addView(frame, ViewGroup.LayoutParams(-1, -2))
            ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
                val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
                view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
                insets
            }
        }
    }

    private fun guide(parent: LinearLayout, number: Int, title: Int, description: Int) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, ui.dp(16), 0, 0)
        }
        row.addView(ui.text(String.format(resources.configuration.locales[0], "%02d", number), 14f, true).apply {
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }, LinearLayout.LayoutParams(ui.dp(36), -2))
        row.addView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(ui.text(getString(title), 16f), block())
            addView(ui.text(getString(description), 14f, true).apply { setLineSpacing(0f, 1.1f) }, block(top = 4))
        }, LinearLayout.LayoutParams(0, -2, 1f))
        parent.addView(row, block())
    }

    private fun renderState(state: Int) {
        if (!::actionButton.isInitialized) return
        val (status, detail) = when (state) {
            ScreenColorPickerStates.STARTING -> R.string.text_starting to R.string.home_starting_detail
            ScreenColorPickerStates.ACTIVE -> R.string.text_active to R.string.text_floating_picker_ready
            ScreenColorPickerStates.STOPPING -> R.string.text_stopping to R.string.home_stopping_detail
            else -> R.string.home_ready to R.string.home_ready_detail
        }
        statusText.setText(status)
        detailText.setText(detail)
        statusMarker.background = ui.fill(if (state == ScreenColorPickerStates.ACTIVE) settingsPalette.accent else settingsPalette.muted, 4)
        actionButton.setText(if (state == ScreenColorPickerStates.ACTIVE) R.string.text_stop_color_picker else R.string.text_start_color_picker)
        actionButton.isEnabled = state == ScreenColorPickerStates.INACTIVE || state == ScreenColorPickerStates.ACTIVE
    }

    private fun block(top: Int = 0) = LinearLayout.LayoutParams(-1, -2).apply { topMargin = ui.dp(top) }

    /** Keep paragraphs readable on tablets while letting narrow windows and large text scroll. */
    private class HomeColumn(context: Context) : LinearLayout(context) {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val maximum = (640 * resources.displayMetrics.density).toInt()
            super.onMeasure(MeasureSpec.makeMeasureSpec(minOf(MeasureSpec.getSize(widthMeasureSpec), maximum), MeasureSpec.EXACTLY), heightMeasureSpec)
        }
    }
}
