package com.ledigana.onboardingsample.ui

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ApplicationComponent {
    fun inject(application: OnboardingApplication)
}

class OnboardingApplication : Application() {
    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
    }
}