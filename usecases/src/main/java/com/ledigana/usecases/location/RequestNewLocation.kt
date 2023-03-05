package com.ledigana.usecases.location

import com.ledigana.domain.model.Location
import com.ledigana.domain.repository.LocationsRepository

class RequestNewLocation(private val locationsRepository: LocationsRepository) {
    operator fun invoke(): List<Location> = locationsRepository.requestNewLocation()
}