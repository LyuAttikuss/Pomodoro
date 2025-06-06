package com.osipenko.pomodoro.presentation

import android.app.Application
import com.osipenko.pomodoro.di.DaggerApplicationComponent

class PomodoroApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}