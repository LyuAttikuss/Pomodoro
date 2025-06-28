package com.osipenko.pomodoro.di

import com.osipenko.pomodoro.core.RunAsync
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreBindsModule {

    @Binds
    abstract fun bindsRunAsync(runAsync: RunAsync.Base) : RunAsync
}