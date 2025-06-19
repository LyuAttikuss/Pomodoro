package com.osipenko.pomodoro.data

import com.osipenko.pomodoro.domain.TaskItem
import javax.inject.Inject

class TaskListMapper @Inject constructor() {

    fun mapEntityToDbModel(taskItem: TaskItem) = TaskItemEntity(
        id = taskItem.id,
        text = taskItem.text
    )

    fun mapDbModelToEntity(taskItemEntity: TaskItemEntity) = TaskItem(
        id = taskItemEntity.id,
        text = taskItemEntity.text
    )
}