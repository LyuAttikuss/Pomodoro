package com.osipenko.pomodoro.domain

import javax.inject.Inject

class AddTaskItemUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend fun addTaskItem(item: TaskItem) {
        taskListRepository.addTaskItem(item)
    }
}