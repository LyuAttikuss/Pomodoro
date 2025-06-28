package com.osipenko.pomodoro.data

import android.content.Context
import androidx.room.Room
import com.osipenko.pomodoro.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface CacheModule {

    fun dao(): TaskListDao

    class Base @Inject constructor(@ApplicationContext applicationContext: Context): CacheModule {

        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                applicationContext.getString(R.string.app_name)
            ).build()
        }

        override fun dao() = database.taskListDao()
    }
}