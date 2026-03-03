package com.osipenko.pomodoro

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(value = AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun startTimerAndChangeButtons(): Unit = with(composeTestRule) {
        setContent {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = "startTimerScreen") {
                composable("startTimerScreen") {
                    TimerScreen(
                        viewModel = TimerViewModel(
                            savedStateHandle = SavedStateHandle(),
                            repository = FakeTimerRepository(),
                            runAsync = FakeRunAsync()
                        )
                    )
                }
            }
        }

        startUiTest()
    }

    @Test
    fun startTimerAndChangeButtonsUi(): Unit = with(composeTestRule) {
        setContent {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = "startTimerScreen") {
                composable("startTimerScreen") {
                    TimerScreenUi(
                        inProgress = false,
                        onStopTimerClick = {},
                        onStartTimerClick = {},
                        onPauseTimerClick = {},
                        onPlayTimerClick = {}
                    )
                }
            }
        }

        startUiTest()
    }

    private fun startUiTest() {
        val timerPage = TimerPage(composeTestRule = composeTestRule)

        timerPage.clickStartTimer()
        timerPage.assertTimerIsRunning()

        timerPage.clickPauseTimer()
        timerPage.assertTimerOnPause()

        timerPage.clickPlayTimer()
        timerPage.assertTimerIsRunning()

        timerPage.clickStopTimer()
        timerPage.assertTimerHasStopped()
    }
}