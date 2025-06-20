package com.osipenko.pomodoro.domain

import com.osipenko.pomodoro.data.TaskItemEntity
import com.osipenko.pomodoro.data.TaskListDao
import javax.inject.Inject

interface TaskListRepository {

    suspend fun taskList(): List<TaskItemEntity>

    class Base @Inject constructor(
        private val dao: TaskListDao
    ): TaskListRepository {

        override suspend fun taskList() = dao.getTaskList()
    }
}