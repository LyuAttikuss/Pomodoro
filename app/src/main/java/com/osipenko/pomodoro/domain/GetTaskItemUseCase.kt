package com.osipenko.pomodoro.domain

import javax.inject.Inject

class GetTaskItemUseCase @Inject constructor(
    private val pomodoroRepository: PomodoroRepository
) {

    suspend fun getTaskItem(id: Long) {
        pomodoroRepository.getTaskItem(id)
    }
}
