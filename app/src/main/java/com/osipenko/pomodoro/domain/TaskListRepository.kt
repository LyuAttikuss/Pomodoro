package com.osipenko.pomodoro.domain

import com.osipenko.pomodoro.data.TaskListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface TaskListRepository {

    val taskList: Flow<List<String>>

    class Base @Inject constructor(
        private val dao: TaskListDao
    ): TaskListRepository {

        override val taskList = dao.getTaskList()
            .map { list ->
                list.map { it.text }
            }
    }
}