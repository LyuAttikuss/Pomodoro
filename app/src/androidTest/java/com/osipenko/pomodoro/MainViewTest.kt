package com.osipenko.pomodoro

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.osipenko.pomodoro.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_button() {
        composeTestRule.apply {
            // onNodeWithTag("taskItemText").assertExists()
            onNodeWithTag("addTaskButton").assertExists()
            onNodeWithTag("addTaskButton").assertHasClickAction()
            onNodeWithTag("addTaskButton").performClick()
            // открыть окно создания задачи
        }
    }
}