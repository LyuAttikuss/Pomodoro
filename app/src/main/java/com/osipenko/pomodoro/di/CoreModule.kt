package com.osipenko.pomodoro.di

import com.osipenko.pomodoro.core.RunAsync
import dagger.Binds
import dagger.Module

@Module
abstract class CoreBindsModule {

    @Binds
    abstract fun bindsRunAsync(runAsync: RunAsync.Base) : RunAsync
}