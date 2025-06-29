package com.osipenko.pomodoro.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osipenko.pomodoro.core.RunAsync
import com.osipenko.pomodoro.domain.TaskListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val runAsync: RunAsync,
    private val repository: TaskListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //val state = repository.taskList().stateIn(viewModelScope)

    private val mutableState = MutableStateFlow<List<String>>(emptyList())
    val state: StateFlow<List<String>>
        get() = mutableState

//    val state: Flow<State> = repository.taskList
//        .filter { it.isNotEmpty() }
//        .map { State.Content(taskList = it) as State }
//        .onStart { emit(State.Loading) }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = State.Loading
//        )

    init {
        runAsync.runFlow(
            scope = viewModelScope,
            flow = repository.taskList,
        ) {
            mutableState.value = it
        }
    }
}