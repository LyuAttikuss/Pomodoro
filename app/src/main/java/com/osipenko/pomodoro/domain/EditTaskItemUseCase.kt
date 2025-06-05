package com.osipenko.pomodoro.domain

import javax.inject.Inject

class EditTaskItemUseCase @Inject constructor(
    private val pomodoroRepository: PomodoroRepository
) {

    suspend fun editTaskItem(item: TaskItem) {
        pomodoroRepository.editTaskItem(item)
    }
}
