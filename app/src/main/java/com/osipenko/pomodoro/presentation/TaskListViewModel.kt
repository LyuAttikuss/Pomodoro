package com.osipenko.pomodoro.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.domain.PomodoroRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: PomodoroRepository,
    private val savedStateHandle: SavedStateHandle

): ViewModel() {

    val state: StateFlow<List<String>> = savedStateHandle.getStateFlow("list", emptyList())

    init {
        viewModelScope.launch {

        }
    }
}