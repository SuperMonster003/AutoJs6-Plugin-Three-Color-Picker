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

class MainActivity : AppCompatActivity() {
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
        root.addView(buildSettingsCard(), LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(20)
        })
        renderState(PickerRuntime.state())
        return ScrollView(this).apply {
            isFillViewport = true
            addView(root, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
    }

    private fun buildSettingsCard(): View {
        val card = MaterialCardView(this).apply {
            radius = dp(28).toFloat()
            cardElevation = dp(2).toFloat()
            setCardBackgroundColor(themeColor(com.google.android.material.R.attr.colorSurfaceContainer))
        }
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(20), dp(24), dp(20))
        }
        content.addView(TextView(this).apply {
            text = getString(R.string.text_picker_settings)
            textSize = 18f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface))
        })
        content.addView(
            optionRow(R.string.text_magnifier_size, PickerSettings.magnifierSizeIndex(this)) { index ->
                PickerSettings.setMagnifierSizeIndex(this, index)
            },
            settingsRowParams(dp(12)),
        )
        content.addView(
            optionRow(R.string.text_capture_range, PickerSettings.captureRangeIndex(this)) { index ->
                PickerSettings.setCaptureRangeIndex(this, index)
            },
            settingsRowParams(dp(4)),
        )
        content.addView(
            optionRow(R.string.text_fine_tune_speed, PickerSettings.fineTuneSpeedIndex(this)) { index ->
                PickerSettings.setFineTuneSpeedIndex(this, index)
            },
            settingsRowParams(dp(4)),
        )
        content.addView(
            switchRow(R.string.text_show_grid_lines, PickerSettings.showGrid(this)) { checked ->
                PickerSettings.setShowGrid(this, checked)
            },
            settingsRowParams(dp(4)),
        )
        content.addView(
            switchRow(R.string.text_copy_numeric_only, PickerSettings.copyNumericOnly(this)) { checked ->
                PickerSettings.setCopyNumericOnly(this, checked)
            },
            settingsRowParams(dp(4)),
        )
        content.addView(TextView(this).apply {
            text = listOf(
                R.string.text_picker_instruction,
                R.string.text_picker_instruction_copy,
                R.string.text_picker_instruction_close,
            ).joinToString("\n") { resId -> getString(resId) }
            textSize = 13f
            setLineSpacing(0f, 1.25f)
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurfaceVariant))
        }, settingsRowParams(dp(14)))
        card.addView(content)
        return card
    }

    private fun settingsRowParams(topMargin: Int): LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            this.topMargin = topMargin
        }

    private fun optionRow(labelRes: Int, selectedIndex: Int, onSelected: (Int) -> Unit): View {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            minimumHeight = dp(48)
        }
        row.addView(TextView(this).apply {
            setText(labelRes)
            textSize = 15f
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface))
        }, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
        row.addView(Spinner(this).apply {
            adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                listOf(
                    getString(R.string.text_option_large),
                    getString(R.string.text_option_medium),
                    getString(R.string.text_option_small),
                ),
            ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            setSelection(selectedIndex.coerceIn(0, PickerSettingsCatalog.OPTION_COUNT - 1), false)
            contentDescription = getString(labelRes)
            minimumHeight = dp(48)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    onSelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        return row
    }

    private fun switchRow(labelRes: Int, checked: Boolean, onChanged: (Boolean) -> Unit): View =
        MaterialSwitch(this).apply {
            setText(labelRes)
            textSize = 15f
            setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface))
            minHeight = dp(48)
            isChecked = checked
            setOnCheckedChangeListener { _, isChecked -> onChanged(isChecked) }
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
        val value = android.util.TypedValue()
        theme.resolveAttribute(attribute, value, true)
        return value.data
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
