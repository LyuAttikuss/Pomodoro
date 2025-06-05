package com.osipenko.pomodoro.data

import com.osipenko.pomodoro.domain.TaskItem
import javax.inject.Inject

class TaskListMapper @Inject constructor() {

    fun mapEntityToDbModel(taskItem: TaskItem) = TaskItemDbModel(
        id = taskItem.id,
        text = taskItem.text
    )

    fun mapDbModelToEntity(taskItemDbModel: TaskItemDbModel) = TaskItem(
        id = taskItemDbModel.id,
        text = taskItemDbModel.text
    )
}