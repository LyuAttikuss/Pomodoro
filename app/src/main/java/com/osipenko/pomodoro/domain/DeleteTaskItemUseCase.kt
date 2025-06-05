package com.osipenko.pomodoro.domain

import javax.inject.Inject

class DeleteTaskItemUseCase @Inject constructor(
    private val pomodoroRepository: PomodoroRepository
) {

    suspend fun deleteTaskItem(item: TaskItem) {
        pomodoroRepository.deleteTaskItem(item)
    }
}
