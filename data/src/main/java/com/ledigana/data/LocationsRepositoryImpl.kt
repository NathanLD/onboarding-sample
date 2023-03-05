package com.ledigana.data

import com.ledigana.domain.model.Location
import com.ledigana.domain.repository.LocationsRepository

class LocationsRepositoryImpl(
    private val locationPersistenceSource: LocationPersistenceSource,
    private val deviceLocationSource: DeviceLocationSource
): LocationsRepository {

    override fun getSavedLocations(): List<Location> = locationPersistenceSource.getPersistedLocations()

    override fun requestNewLocation(): List<Location> {
        val newLocation = deviceLocationSource.getDeviceLocation()
        locationPersistenceSource.saveNewLocation(newLocation)
        return getSavedLocations()
    }

}

interface LocationPersistenceSource {
    fun getPersistedLocations(): List<Location>
    fun saveNewLocation(location: Location)
}

interface DeviceLocationSource {
    fun getDeviceLocation(): Location
}