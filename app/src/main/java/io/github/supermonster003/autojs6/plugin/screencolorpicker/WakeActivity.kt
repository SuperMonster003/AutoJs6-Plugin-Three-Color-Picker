package io.github.supermonster003.autojs6.plugin.screencolorpicker

import android.app.Activity
import android.os.Bundle

/** Removes the package stopped state when AutoJs6 discovers an installed plugin. */
class WakeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
    }
}
