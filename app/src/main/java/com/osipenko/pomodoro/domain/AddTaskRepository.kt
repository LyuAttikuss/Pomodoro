package com.osipenko.pomodoro.domain

import com.osipenko.pomodoro.data.TaskItemEntity
import com.osipenko.pomodoro.data.TaskListDao
import javax.inject.Inject

interface AddTaskRepository {

    suspend fun addTask(text: String)

    class Base @Inject constructor(
        private val dao: TaskListDao
    ): AddTaskRepository {

        override suspend fun addTask(text: String) {
            dao.addTaskItem(TaskItemEntity(System.currentTimeMillis(), text))
        }
    }
}