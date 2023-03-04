package com.ledigana.onboardingsample.framework.location

import com.ledigana.data.LocationPersistenceSource
import com.ledigana.domain.Location

class InMemoryLocationPersistenceSource : LocationPersistenceSource {

    private var locations: List<Location> = emptyList()

    override fun getPersistedLocations(): List<Location> = locations

    override fun saveNewLocation(location: Location) {
        locations = locations + location
    }
}