package com.osipenko.pomodoro.domain

import com.osipenko.pomodoro.data.TaskListDao
import com.osipenko.pomodoro.data.TaskListMapper

interface PomodoroRepository {

    suspend fun addTaskItem(item: TaskItem)

    suspend fun deleteTaskItem(item: TaskItem)

    suspend fun editTaskItem(item: TaskItem)

    suspend fun getTaskItem(id: Long): TaskItem

    class Base(
        private val dao: TaskListDao,
        private val mapper: TaskListMapper
    ): PomodoroRepository {

        override suspend fun addTaskItem(item: TaskItem) {
            dao.addTaskItem(mapper.mapEntityToDbModel(item))
        }

        override suspend fun deleteTaskItem(item: TaskItem) {
            dao.deleteTaskItem(item.id)
        }

        override suspend fun editTaskItem(item: TaskItem) {
            dao.addTaskItem(mapper.mapEntityToDbModel(item))
        }

        override suspend fun getTaskItem(id: Long): TaskItem {
            val taskItemDbModel = dao.getTaskItem(id)
            return mapper.mapDbModelToEntity(taskItemDbModel)
        }
    }
}