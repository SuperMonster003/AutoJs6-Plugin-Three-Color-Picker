package io.github.supermonster003.autojs6.plugin.screencolorpicker

import org.junit.Assert.assertEquals
import org.junit.Test

class PickerRequestRestoreStateTest {
    private val dispatched = PickerRequestRestoreState(
        overlaySettingsLaunched = true,
        initialAdvanceDone = true,
        notificationRequestLaunched = true,
        projectionRequestLaunched = true,
        startDispatched = true,
        ownsStart = true,
    )

    @Test
    fun sameProcessRecreationPreservesInFlightServiceWait() {
        assertEquals(dispatched, dispatched.restoredForProcess("same", "same"))
    }

    @Test
    fun processRecreationInvalidatesConsumedProjectionResultAndServiceDispatch() {
        assertEquals(
            PickerRequestRestoreState(
                overlaySettingsLaunched = false,
                initialAdvanceDone = false,
                notificationRequestLaunched = false,
                projectionRequestLaunched = false,
                startDispatched = false,
                ownsStart = false,
            ),
            dispatched.restoredForProcess("old", "new"),
        )
    }

    @Test
    fun processRecreationKeepsSystemConsentRequestThatHasNotReturned() {
        val consentPending = dispatched.copy(startDispatched = false)
        assertEquals(consentPending, consentPending.restoredForProcess("old", "new"))
    }
}
