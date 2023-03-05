package com.ledigana.domain.repository

interface OnboardingRepository {
    fun setAsViewed(name: String)
    fun viewed(name: String): Boolean
}