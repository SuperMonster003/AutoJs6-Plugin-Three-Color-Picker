package io.github.supermonster003.autojs6.plugin.three.color.picker

/** Saved permission-flow flags with process-local work removed after process recreation. */
internal data class PickerRequestRestoreState(
    val overlaySettingsLaunched: Boolean,
    val initialAdvanceDone: Boolean,
    val notificationRequestLaunched: Boolean,
    val projectionRequestLaunched: Boolean,
    val startDispatched: Boolean,
    val ownsStart: Boolean,
) {
    fun restoredForProcess(savedProcessInstanceId: String?, currentProcessInstanceId: String): PickerRequestRestoreState {
        if (!startDispatched || savedProcessInstanceId == currentProcessInstanceId) return this
        // A consumed MediaProjection result cannot survive process death. Begin again from the
        // visible permission flow instead of waiting for a foreground service that no longer exists.
        return copy(
            overlaySettingsLaunched = false,
            initialAdvanceDone = false,
            notificationRequestLaunched = false,
            projectionRequestLaunched = false,
            startDispatched = false,
            ownsStart = false,
        )
    }
}
