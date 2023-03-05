package com.ledigana.usecases.onboarding

import com.ledigana.domain.repository.OnboardingRepository

class SetOnboardingAsViewed(private val onboardingRepository: OnboardingRepository, private val name: String) {
    operator fun invoke(): Unit = onboardingRepository.setAsViewed(name)
}