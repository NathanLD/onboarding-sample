package com.ledigana.domain.repository

import com.ledigana.domain.model.Location

interface OnboardingRepository {
    fun setAsViewed(name: String)
    fun viewed(name: String): Boolean
}