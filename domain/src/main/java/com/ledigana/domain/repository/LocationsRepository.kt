package com.ledigana.domain.repository

import com.ledigana.domain.model.Location

interface LocationsRepository {
    fun getSavedLocations(): List<Location>
    fun requestNewLocation(): List<Location>
}