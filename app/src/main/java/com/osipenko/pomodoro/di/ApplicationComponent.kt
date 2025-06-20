package com.osipenko.pomodoro.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
//@Component(
//    modules = [DataModule::class]
//)
interface ApplicationComponent {

    //fun inject(application: Application)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
