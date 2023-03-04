package com.ledigana.usecases

import com.ledigana.data.LocationsRepository
import com.ledigana.domain.Location

class RequestNewLocation(private val locationsRepository: LocationsRepository) {

    operator fun invoke(): List<Location> = locationsRepository.requestNewLocation()

}