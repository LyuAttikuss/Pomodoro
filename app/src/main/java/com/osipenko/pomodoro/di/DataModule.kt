package com.osipenko.pomodoro.di

import android.app.Application
import com.osipenko.pomodoro.data.AppDatabase
import com.osipenko.pomodoro.data.PomodoroRepositoryImpl
import com.osipenko.pomodoro.data.TaskListDao
import com.osipenko.pomodoro.domain.PomodoroRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindPomodoroRepository(repositoryImpl: PomodoroRepositoryImpl): PomodoroRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideTaskListDao(application: Application): TaskListDao {
            return AppDatabase.getInstance(application).taskListDao()
        }
    }
}
