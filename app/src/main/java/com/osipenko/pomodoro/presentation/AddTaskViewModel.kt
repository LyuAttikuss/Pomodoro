package com.osipenko.pomodoro.presentation

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.core.RunAsync
import com.osipenko.pomodoro.domain.AddTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.String.format
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val runAsync: RunAsync,
    private val addTaskRepository: AddTaskRepository
) : ViewModel() {

    private val closeMutableStateFlow = MutableStateFlow(false)
    val state: StateFlow<Boolean>
        get() = closeMutableStateFlow

    private val timerValueStateFlow = MutableStateFlow("25:00")
    val timerValue: StateFlow<String>
        get() = timerValueStateFlow

    private val totalTimeInMillis: Long = 25 * 60 * 1000
    private val countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            timerValueStateFlow.value = formatTimerValue(millisUntilFinished)
        }

        override fun onFinish() {

        }
    }

    private fun formatTimerValue(millis: Long) : String {
        val minutes = millis / 1000 / 60
        val seconds = millis / 1000 % 60
        return format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    fun add(text: String) {
        runAsync.runAsync(
            scope = viewModelScope,
            background = {
                addTaskRepository.addTask(text)
            }
        ) {
            closeMutableStateFlow.value = true
        }
    }

    fun startTimer() {
        countDownTimer.start()
    }

    fun stopTimer() {
        countDownTimer.cancel()
        timerValueStateFlow.value = formatTimerValue(totalTimeInMillis)
    }

    fun pauseTimer() {
        countDownTimer.cancel()
    }

    fun nextPeriodTimer() {

    }
}