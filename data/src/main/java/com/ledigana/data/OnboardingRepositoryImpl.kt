package com.ledigana.data

import com.ledigana.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val preferencesSource: PreferencesSource,
): OnboardingRepository {

    override fun setAsViewed(name: String) {
        preferencesSource.setBool(name, true)
    }

    override fun viewed(name: String): Boolean {
        return preferencesSource.getBool(name)
    }
}

interface PreferencesSource {
    fun getBool(name: String): Boolean
    fun setBool(name: String, value: Boolean)
}