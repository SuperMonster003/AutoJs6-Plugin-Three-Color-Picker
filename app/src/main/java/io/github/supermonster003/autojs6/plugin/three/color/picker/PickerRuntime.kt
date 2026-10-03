package io.github.supermonster003.autojs6.plugin.three.color.picker

import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates
import java.util.concurrent.CopyOnWriteArraySet
import java.util.UUID

/** A small deterministic state machine shared by the launcher, Binder, and FGS. */
internal class PickerStateMachine(
    initialState: Int = ScreenColorPickerStates.INACTIVE,
) {
    private var value = ScreenColorPickerStates.requireKnown(initialState)

    @Synchronized
    fun state(): Int = value

    @Synchronized
    fun beginStart(): Boolean {
        if (value != ScreenColorPickerStates.INACTIVE) return false
        value = ScreenColorPickerStates.STARTING
        return true
    }

    @Synchronized
    fun markActive(): Boolean {
        if (value != ScreenColorPickerStates.STARTING) return false
        value = ScreenColorPickerStates.ACTIVE
        return true
    }

    @Synchronized
    fun beginStop(): Boolean {
        if (value == ScreenColorPickerStates.INACTIVE || value == ScreenColorPickerStates.STOPPING) {
            return false
        }
        value = ScreenColorPickerStates.STOPPING
        return true
    }

    @Synchronized
    fun markInactive(): Boolean {
        if (value == ScreenColorPickerStates.INACTIVE) return false
        value = ScreenColorPickerStates.INACTIVE
        return true
    }
}

internal object PickerRuntime {
    private val machine = PickerStateMachine()
    private val listeners = CopyOnWriteArraySet<(Int) -> Unit>()
    private val processInstanceId = UUID.randomUUID().toString()

    fun state(): Int = machine.state()

    fun processInstanceId(): String = processInstanceId

    fun beginStart(): Boolean = machine.beginStart().also { if (it) notifyState() }

    fun markActive(): Boolean = machine.markActive().also { if (it) notifyState() }

    fun beginStop(): Boolean = machine.beginStop().also { if (it) notifyState() }

    fun markInactive(): Boolean = machine.markInactive().also { if (it) notifyState() }

    fun addListener(listener: (Int) -> Unit) {
        listeners += listener
        listener(state())
    }

    fun removeListener(listener: (Int) -> Unit) {
        listeners -= listener
    }

    private fun notifyState() {
        val state = state()
        listeners.forEach { listener -> runCatching { listener(state) } }
    }
}
