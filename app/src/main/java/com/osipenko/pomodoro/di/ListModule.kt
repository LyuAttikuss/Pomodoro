package com.osipenko.pomodoro.di

import com.osipenko.pomodoro.data.CacheModule
import com.osipenko.pomodoro.data.TaskListDao
import com.osipenko.pomodoro.domain.TaskListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    companion object {

        @Provides
        @ApplicationScope
        fun provideTaskListDao(cacheModule: CacheModule): TaskListDao {
            return cacheModule.dao()
        }
    }
}

@Module
abstract class TaskListBindModule {

    @Binds
    abstract fun bindsListRepository(repository: TaskListRepository.Base) : TaskListRepository

    @Singleton
    @Binds
    abstract fun bindCacheModule(cacheModule: CacheModule): CacheModule
}
