package com.osipenko.pomodoro.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao
}