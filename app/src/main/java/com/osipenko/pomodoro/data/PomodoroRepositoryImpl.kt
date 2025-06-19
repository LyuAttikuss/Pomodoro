package com.osipenko.pomodoro.data

import com.osipenko.pomodoro.domain.PomodoroRepository
import com.osipenko.pomodoro.domain.TaskItem
import javax.inject.Inject

class PomodoroRepositoryImpl @Inject constructor(
    private val taskListDao: TaskListDao,
    private val mapper: TaskListMapper
): PomodoroRepository {

    override suspend fun addTaskItem(item: TaskItem) {
        taskListDao.addTaskItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun deleteTaskItem(item: TaskItem) {
        taskListDao.deleteTaskItem(item.id)
    }

    override suspend fun editTaskItem(item: TaskItem) {
        taskListDao.addTaskItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getTaskItem(id: Long): TaskItem {
        val taskItemDbModel = taskListDao.getTaskItem(id)
        return mapper.mapDbModelToEntity(taskItemDbModel)
    }
}