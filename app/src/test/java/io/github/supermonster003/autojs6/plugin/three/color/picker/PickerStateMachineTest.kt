package io.github.supermonster003.autojs6.plugin.three.color.picker

import org.autojs.plugin.screencolorpicker.api.ScreenColorPickerStates
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class PickerStateMachineTest {
    @Test
    fun normalLifecycleAndRepeatedOperationsAreDeterministic() {
        val machine = PickerStateMachine()
        assertEquals(ScreenColorPickerStates.INACTIVE, machine.state())
        assertTrue(machine.beginStart())
        assertFalse(machine.beginStart())
        assertTrue(machine.markActive())
        assertFalse(machine.markActive())
        assertTrue(machine.beginStop())
        assertFalse(machine.beginStop())
        assertTrue(machine.markInactive())
        assertFalse(machine.markInactive())
    }

    @Test
    fun startCanBeCancelledBeforeBecomingActive() {
        val machine = PickerStateMachine()
        assertTrue(machine.beginStart())
        assertTrue(machine.beginStop())
        assertFalse(machine.markActive())
        assertTrue(machine.markInactive())
    }

    @Test
    fun invalidInitialStateIsRejected() {
        assertThrows(IllegalArgumentException::class.java) { PickerStateMachine(99) }
    }

    @Test
    fun concurrentStartHasExactlyOneWinner() {
        val machine = PickerStateMachine()
        val winners = AtomicInteger()
        val ready = CountDownLatch(24)
        val go = CountDownLatch(1)
        val pool = Executors.newFixedThreadPool(24)
        repeat(24) {
            pool.execute {
                ready.countDown()
                go.await()
                if (machine.beginStart()) winners.incrementAndGet()
            }
        }
        assertTrue(ready.await(2, TimeUnit.SECONDS))
        go.countDown()
        pool.shutdown()
        assertTrue(pool.awaitTermination(2, TimeUnit.SECONDS))
        assertEquals(1, winners.get())
        assertEquals(ScreenColorPickerStates.STARTING, machine.state())
    }
}
