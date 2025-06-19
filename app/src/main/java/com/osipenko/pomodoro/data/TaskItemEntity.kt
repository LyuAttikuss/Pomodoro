package com.osipenko.pomodoro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_items")
data class TaskItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val text: String
)