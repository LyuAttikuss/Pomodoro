package com.osipenko.pomodoro.presentation

import java.lang.String.format
import java.util.Locale

class TimerState(
    val period: Period = Period.WorkPeriod(WORK_PERIOD_IN_MILLIS, 0),
    val inProgress: Boolean = false,
    val currentValue: String = WORK_PERIOD_VALUE,
    val currentTimeInMillis: Long = WORK_PERIOD_IN_MILLIS,
    val isLast: Boolean = false
) {
    companion object {

        const val WORK_PERIOD_COUNT = 4
        const val RELAX_PERIOD_COUNT = 3
        const val WORK_PERIOD_VALUE = "25:00"
        const val WORK_PERIOD_IN_MILLIS = 1_500_000L
        const val RELAX_SHORT_PERIOD_IN_MILLIS = 300_000L
        const val RELAX_LONG_PERIOD_IN_MILLIS = 900_000L
    }
}