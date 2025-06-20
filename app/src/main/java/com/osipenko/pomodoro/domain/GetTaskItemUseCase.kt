package com.osipenko.pomodoro.domain

import javax.inject.Inject

class GetTaskItemUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend fun getTaskItem(id: Long) {
        taskListRepository.getTaskItem(id)
    }
}
