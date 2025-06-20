package com.osipenko.pomodoro.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskListDao {

    @Query("SELECT * FROM task_items WHERE id=:itemId LIMIT 1")
    suspend fun getTaskItem(itemId: Long): TaskItemEntity

    @Query("SELECT * FROM task_items")
    suspend fun getTaskList(): List<TaskItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskItem(item: TaskItemEntity)

    @Query("DELETE FROM task_items WHERE id=:itemId")
    suspend fun deleteTaskItem(itemId: Long)
}