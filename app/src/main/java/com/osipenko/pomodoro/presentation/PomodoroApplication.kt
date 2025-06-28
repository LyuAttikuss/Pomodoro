package com.osipenko.pomodoro.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PomodoroApplication: Application() {

    val component by lazy {
        //DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}