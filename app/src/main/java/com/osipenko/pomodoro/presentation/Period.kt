package com.osipenko.pomodoro.presentation

sealed class Period {

    data class WorkPeriod(
        val timeInMillis: Long,
        val index: Int
    ) : Period()

    data class RelaxPeriod(
        val timeInMillis: Long,
        val index: Int
    ): Period()
}