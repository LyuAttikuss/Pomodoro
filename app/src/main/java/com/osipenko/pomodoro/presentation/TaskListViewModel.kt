package com.osipenko.pomodoro.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.core.RunAsync
import com.osipenko.pomodoro.domain.TaskListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val runAsync: RunAsync,
    private val repository: TaskListRepository,
    private val savedStateHandle: SavedStateHandle

): ViewModel() {

    val state: StateFlow<List<String>> = savedStateHandle.getStateFlow("list", emptyList())

    init {
        runAsync.runAsync(
            scope = viewModelScope,
            background = repository::taskList
        ) {
            savedStateHandle["list"] = it
        }
    }
}