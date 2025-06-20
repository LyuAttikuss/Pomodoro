package com.osipenko.pomodoro.domain

import javax.inject.Inject

class DeleteTaskItemUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend fun deleteTaskItem(item: TaskItem) {
        taskListRepository.deleteTaskItem(item)
    }
}
