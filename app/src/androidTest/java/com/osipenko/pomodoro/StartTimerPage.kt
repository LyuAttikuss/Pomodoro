package com.osipenko.pomodoro

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

class StartTimerPage(private val composeTestRule: ComposeContentTestRule) {

    private val timerLoader = composeTestRule.onNodeWithTag("timerLoaderUi")
    private val startButton = composeTestRule.onNodeWithTag("startTimerButton")
    private val playButton = composeTestRule.onNodeWithTag("playButton")
    private val stopButton = composeTestRule.onNodeWithTag("stopButton")
    private val pauseButton = composeTestRule.onNodeWithTag("pauseButton")

    // TODO: check timer is loading
    fun clickStartTimer() {
        startButton.performClick()
    }

    fun assertTimerIsRunning() {
        timerLoader.assertIsDisplayed()
        startButton.assertDoesNotExist()
        playButton.assertDoesNotExist()
        stopButton.assertExists().assertHasClickAction()
        pauseButton.assertExists().assertHasClickAction()
    }

    fun clickPauseTimer() {
        pauseButton.performClick()
    }

    // TODO: check timer is not loading
    fun assertTimerOnPause() {
        timerLoader.assertIsDisplayed()
        startButton.assertDoesNotExist()
        pauseButton.assertDoesNotExist()
        playButton.assertExists().assertHasClickAction()
        stopButton.assertExists().assertHasClickAction()
    }

    fun clickPlayTimer() {
        playButton.performClick()
    }

    fun clickStopTimer() {
        stopButton.performClick()
    }

    fun assertTimerHasStopped() {
        timerLoader.assertDoesNotExist()
        pauseButton.assertDoesNotExist()
        playButton.assertDoesNotExist()
        stopButton.assertDoesNotExist()
        startButton.assertExists().assertHasClickAction()
    }
}
