package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.materialswitch.MaterialSwitch
import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates

class MainActivity : AppearanceActivity() {
    private lateinit var statusText: TextView
    private lateinit var detailText: TextView
    private lateinit var actionButton: MaterialButton

    private val startPicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        renderState(PickerRuntime.state())
        if (result.resultCode == Activity.RESULT_OK) moveTaskToBack(true)
    }

    private val stateListener: (Int) -> Unit = { state -> runOnUiThread { renderState(state) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(buildContent())
    }

    override fun onStart() {
        super.onStart()
        PickerRuntime.addListener(stateListener)
    }

    override fun onStop() {
        PickerRuntime.removeListener(stateListener)
        super.onStop()
    }

    private fun buildContent(): View {
        val horizontal = dp(24)
        val vertical = dp(20)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(horizontal, vertical, horizontal, vertical)
        }
        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(horizontal + bars.left, vertical + bars.top, horizontal + bars.right, vertical + bars.bottom)
            insets
        }

        val title = TextView(this).apply {
            text = getString(R.string.app_name)
            textSize = 30f
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface))
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }
        root.addView(title, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(44)
        })

        root.addView(TextView(this).apply {
            text = getString(R.string.text_color_picker_home_description)
            textSize = 17f
            setLineSpacing(0f, 1.2f)
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurfaceVariant))
        }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(12)
        })

        val card = MaterialCardView(this).apply {
            radius = dp(28).toFloat()
            cardElevation = dp(2).toFloat()
            setCardBackgroundColor(themeColor(com.google.android.material.R.attr.colorSurfaceContainer))
        }
        val cardContent = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(24), dp(32), dp(24), dp(28))
        }
        cardContent.addView(HomePickerGlyph(this), LinearLayout.LayoutParams(dp(112), dp(112)))
        statusText = TextView(this).apply {
            gravity = Gravity.CENTER
            textSize = 18f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface))
        }
        cardContent.addView(statusText, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(24)
        })
        detailText = TextView(this).apply {
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurfaceVariant))
        }
        cardContent.addView(detailText, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(8)
        })
        actionButton = MaterialButton(this).apply {
            isAllCaps = false
            minHeight = dp(52)
            setOnClickListener {
                if (PickerRuntime.state() == ScreenColorPickerStates.ACTIVE) {
                    ProjectionForegroundService.requestStop(this@MainActivity)
                } else {
                    startPicker.launch(Intent(this@MainActivity, PickerRequestActivity::class.java))
                }
            }
        }
        cardContent.addView(actionButton, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(28)
        })
        card.addView(cardContent)
        root.addView(card, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(40)
        })
        root.addView(MaterialButton(this).apply {
            tag="open-settings"
            setText(R.string.settings_title)
            isAllCaps=false
            setOnClickListener { startActivity(Intent(this@MainActivity,AppSettingsActivity::class.java)) }
            SettingsUi(this@MainActivity,settingsPalette).tint(this)
        },LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin=dp(20) })
        root.addView(MaterialButton(this).apply {
            setText(R.string.release_history)
            setOnClickListener { showReleaseHistory() }
        })
        SettingsUi(this,settingsPalette).tint(root)
        renderState(PickerRuntime.state())
        return ScrollView(this).apply {
            isFillViewport = true
            addView(root, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
    }

    private fun renderState(state: Int) {
        if (!::statusText.isInitialized || !::actionButton.isInitialized) return
        statusText.setText(
            when (state) {
                ScreenColorPickerStates.STARTING -> R.string.text_starting
                ScreenColorPickerStates.ACTIVE -> R.string.text_active
                ScreenColorPickerStates.STOPPING -> R.string.text_stopping
                else -> R.string.text_inactive
            },
        )
        actionButton.setText(
            if (state == ScreenColorPickerStates.ACTIVE) {
                R.string.text_stop_color_picker
            } else {
                R.string.text_start_color_picker
            },
        )
        detailText.setText(
            if (state == ScreenColorPickerStates.ACTIVE) {
                R.string.text_floating_picker_ready
            } else {
                R.string.text_picker_instruction
            },
        )
        actionButton.isEnabled = state == ScreenColorPickerStates.INACTIVE || state == ScreenColorPickerStates.ACTIVE
    }

    private fun themeColor(attribute: Int): Int {
        return when(attribute) {
            com.google.android.material.R.attr.colorOnSurface -> settingsPalette.text
            com.google.android.material.R.attr.colorOnSurfaceVariant -> settingsPalette.muted
            com.google.android.material.R.attr.colorSurfaceContainer,com.google.android.material.R.attr.colorSurface -> settingsPalette.surface
            androidx.appcompat.R.attr.colorPrimary -> settingsPalette.primary
            else -> settingsPalette.accent
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
}

private class HomePickerGlyph(context: android.content.Context) : View(context) {
    private val paint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: android.graphics.Canvas) {
        val radius = minOf(width, height) * 0.45f
        val centerX = width / 2f
        val centerY = height / 2f
        paint.shader = android.graphics.LinearGradient(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            intArrayOf(Color.rgb(103, 80, 164), Color.rgb(0, 161, 150), Color.rgb(255, 111, 97)),
            null,
            android.graphics.Shader.TileMode.CLAMP,
        )
        canvas.drawCircle(centerX, centerY, radius, paint)
        paint.shader = null
        paint.style = android.graphics.Paint.Style.STROKE
        paint.strokeWidth = radius * 0.10f
        paint.color = Color.WHITE
        canvas.drawCircle(centerX, centerY, radius * 0.35f, paint)
        canvas.drawLine(centerX, centerY - radius * 0.55f, centerX, centerY - radius * 0.15f, paint)
        canvas.drawLine(centerX, centerY + radius * 0.15f, centerX, centerY + radius * 0.55f, paint)
        canvas.drawLine(centerX - radius * 0.55f, centerY, centerX - radius * 0.15f, centerY, paint)
        canvas.drawLine(centerX + radius * 0.15f, centerY, centerX + radius * 0.55f, centerY, paint)
        paint.style = android.graphics.Paint.Style.FILL
    }
}
