package com.osipenko.pomodoro.domain

import javax.inject.Inject

class EditTaskItemUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend fun editTaskItem(item: TaskItem) {
        taskListRepository.editTaskItem(item)
    }
}
