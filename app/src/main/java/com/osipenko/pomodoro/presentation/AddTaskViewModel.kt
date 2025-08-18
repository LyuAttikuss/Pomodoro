package com.osipenko.pomodoro.presentation

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.core.RunAsync
import com.osipenko.pomodoro.domain.AddTaskRepository
import com.osipenko.pomodoro.presentation.TimerState.Companion.WORK_PERIOD_IN_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

//    private val timerValueStateFlow = MutableStateFlow("25:00")
//    val timerValue: StateFlow<String>
//        get() = timerValueStateFlow

    private val totalTimeInMillis: Long = 25 * 60 * 1000
//    private val currentTimeInMillisStateFlow = MutableStateFlow(WORK_PERIOD_IN_MILLIS)
//    val currentTimeInMillis: StateFlow<Long>
//        get() = currentTimeInMillisStateFlow

    private val timerStateFlow = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState>
        get() = timerStateFlow

    private val countDownTimer = object : CountDownTimer(
        timerStateFlow.value.currentTimeInMillis,
        TIMER_INTERVAL
    ) {

        override fun onTick(millisUntilFinished: Long) {
            //currentTimeInMillisStateFlow.value = millisUntilFinished
            //timerValueStateFlow.value = formatTimerValue(millisUntilFinished)

            timerStateFlow.value = TimerState(
                period = timerStateFlow.value.period,
                inProgress = timerStateFlow.value.inProgress,
                currentValue = formatTimerValue(millisUntilFinished),
                currentTimeInMillis = millisUntilFinished
            )
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
            initialTimer()
        }
    }

    private fun initialTimer() {
        timerStateFlow.value = TimerState(
            period = Period.WorkPeriod(WORK_PERIOD_IN_MILLIS, 0),
            inProgress = true
        )
        countDownTimer.start()
    }

    fun startTimer() {
        countDownTimer.onTick(timerStateFlow.value.currentTimeInMillis)
        val timerStateValue = timerStateFlow.value
        timerStateFlow.value = TimerState(
            period = timerStateValue.period,
            inProgress = true,
            currentValue = timerStateValue.currentValue,
            currentTimeInMillis = timerStateValue.currentTimeInMillis
        )
        countDownTimer.start()
    }

    fun stopTimer() {
        countDownTimer.cancel()
        timerStateFlow.value = TimerState()
    }

    fun pauseTimer() {
        countDownTimer.cancel()

        val timerStateValue = timerStateFlow.value
        timerStateFlow.value = TimerState(
            period = timerStateValue.period,
            inProgress = false,
            currentValue = timerStateValue.currentValue,
            currentTimeInMillis = timerStateValue.currentTimeInMillis
        )
    }

    fun nextPeriodTimer() {
        val timerStateValue = timerStateFlow.value
        val nextPeriod: Period
        val nextPeriodInMillis: Long

        when (timerStateValue.period) {
            is Period.WorkPeriod -> {
                val periodInMillis = if (timerStateValue.period.index > 2)
                    TimerState.RELAX_LONG_PERIOD_IN_MILLIS
                else
                    TimerState.RELAX_SHORT_PERIOD_IN_MILLIS
                nextPeriod = Period.RelaxPeriod(periodInMillis, timerStateValue.period.index)
                nextPeriodInMillis = periodInMillis
            }
            is Period.RelaxPeriod -> {
                if (timerStateValue.period.index > 2) {
                    stopTimer()
                    return
                } else {
                    nextPeriod = Period.WorkPeriod(
                        WORK_PERIOD_IN_MILLIS,
                        timerStateValue.period.index + 1
                    )
                    nextPeriodInMillis = WORK_PERIOD_IN_MILLIS
                }
            }
        }

        timerStateFlow.value = TimerState(
            period = nextPeriod,
            inProgress = true
        )

        countDownTimer.onTick(nextPeriodInMillis)
    }

    companion object {

        private const val TIMER_INTERVAL = 1000L
    }
}