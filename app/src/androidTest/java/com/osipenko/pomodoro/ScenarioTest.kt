package com.osipenko.pomodoro

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.osipenko.pomodoro.presentation.StartTaskScreen
import com.osipenko.pomodoro.presentation.TimerScreen
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
                composable("startTaskScreen") {
                    StartTaskScreen(
                        viewModel = StartTaskViewModel(
                            savedStateHandle = SavedStateHandle(),
                            repository = FakeStartTaskRepository(),
                            runAsync = FakeRunAsync()
                        ),
                        navigateToTimerScreen = {
                            navController.navigate("timerScreen")
                        }
                    )
                }

                composable("timerScreen") {
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
                composable("startTaskScreen") {
                    val input = rememberSaveable { mutableStateOf("") }

                    StartTaskScreenUi(
                        input = input.value,
                        onInputChange = { text: String ->
                            input.value = text
                        },
                        onStartTaskClick = { taskName: String ->
                            navController.navigate("timerScreen")
                        }
                    )
                }

                composable("timerScreen") {
                    TimerScreenUi.Base(
                        taskName = "task",
                        inProgress = true
                    ).Show()
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