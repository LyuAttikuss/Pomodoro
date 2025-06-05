package com.osipenko.pomodoro.domain

interface PomodoroRepository {

    suspend fun addTaskItem(item: TaskItem)

    suspend fun deleteTaskItem(item: TaskItem)

    suspend fun editTaskItem(item: TaskItem)

    suspend fun getTaskItem(id: Int): TaskItem
}
