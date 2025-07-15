package com.osipenko.pomodoro

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `timer executes task after delay`() = runBlocking {
        var taskExecuted = false

        val task: suspend () -> Unit = {
            taskExecuted = true
        }

//        startTimer(delay = 1000L, task = task)
//
//        delay(1500L)
        assertTrue(taskExecuted)
    }
}