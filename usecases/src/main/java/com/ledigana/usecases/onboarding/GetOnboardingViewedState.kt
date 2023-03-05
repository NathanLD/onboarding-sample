package com.ledigana.usecases.onboarding

import com.ledigana.domain.repository.OnboardingRepository

class GetOnboardingViewedState(private val onboardingRepository: OnboardingRepository, private val name: String) {
    operator fun invoke(): Boolean = onboardingRepository.viewed(name)
}