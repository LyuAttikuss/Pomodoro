package com.osipenko.pomodoro.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.core.RunAsync
import com.osipenko.pomodoro.domain.AddTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val runAsync: RunAsync,
    private val addTaskRepository: AddTaskRepository
) : ViewModel() {

    private val closeMutableStateFlow = MutableStateFlow(false)
    val state: StateFlow<Boolean>
        get() = closeMutableStateFlow

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
}