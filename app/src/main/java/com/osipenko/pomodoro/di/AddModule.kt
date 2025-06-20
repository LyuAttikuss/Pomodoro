package com.osipenko.pomodoro.di

import com.osipenko.pomodoro.data.CacheModule
import com.osipenko.pomodoro.domain.AddTaskRepository
import com.osipenko.pomodoro.domain.TaskListRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AddTaskBindModule {

    @Binds
    abstract fun bindsAddTaskRepository(repository: AddTaskRepository.Base) : AddTaskRepository

    @Binds
    abstract fun bindCacheModule(cacheModule: CacheModule): CacheModule
}