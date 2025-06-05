package com.osipenko.pomodoro.domain

data class TaskItem (
    val text: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
