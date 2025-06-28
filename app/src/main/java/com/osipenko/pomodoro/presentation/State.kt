package com.osipenko.pomodoro.presentation

sealed class State {
    object Initial : State()
    object Loading : State()
    data class Content(val taskList: List<String>) : State()
}