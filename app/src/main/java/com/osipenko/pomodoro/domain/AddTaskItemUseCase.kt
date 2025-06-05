package com.osipenko.pomodoro.domain

import javax.inject.Inject

class AddTaskItemUseCase @Inject constructor(
    private val pomodoroRepository: PomodoroRepository
) {

    suspend fun addTaskItem(item: TaskItem) {
        pomodoroRepository.addTaskItem(item)
    }
}