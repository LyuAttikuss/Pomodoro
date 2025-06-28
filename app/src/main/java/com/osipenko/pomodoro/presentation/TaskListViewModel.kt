package com.osipenko.pomodoro.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.core.RunAsync
import com.osipenko.pomodoro.domain.TaskListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val runAsync: RunAsync,
    private val repository: TaskListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //val state = repository.taskList().stateIn(viewModelScope)

    val state: Flow<State> = repository.taskList
        .filter { it.isNotEmpty() }
        .map { State.Content(taskList = it) as State }
        .onStart { emit(State.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = State.Loading
        )

//    init {
//        viewModelScope.launch {
//            repository.taskList()
//        }
//        load()
//    }
//
//    fun load() {
//        runAsync.runAsync(
//            scope = viewModelScope,
//            background = repository::taskList
//        ) {
//            savedStateHandle["list"] = it
//        }
//    }
}