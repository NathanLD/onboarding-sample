package com.ledigana.onboardingsample.ui

import android.app.Application
import com.ledigana.onboardingsample.framework.onboarding.AppPreferences

class OnboardingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}