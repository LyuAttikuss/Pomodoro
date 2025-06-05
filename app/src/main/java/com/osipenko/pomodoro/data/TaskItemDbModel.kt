package com.osipenko.pomodoro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_items")
data class TaskItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String
)