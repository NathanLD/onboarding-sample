package com.ledigana.onboardingsample.ui

import com.ledigana.onboardingsample.ui.location.Location
import com.ledigana.onboardingsample.ui.location.toPresentationModel
import com.ledigana.domain.model.Location as LocationDomain
import com.ledigana.usecases.location.GetLocations
import com.ledigana.usecases.location.RequestNewLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(
    private var view: View?,
    private val getLocations: GetLocations,
    private val requestNewLocation: RequestNewLocation
) {
    interface View {
        fun renderLocations(locations: List<Location>)
    }

    fun onCreate() = GlobalScope.launch(Dispatchers.Main) {
        val locations = withContext(Dispatchers.IO) { getLocations() }
        view?.renderLocations(locations.map(LocationDomain::toPresentationModel))
    }

    fun newLocationClicked() = GlobalScope.launch(Dispatchers.Main) {
        val locations = withContext(Dispatchers.IO) { requestNewLocation() }
        view?.renderLocations(locations.map(LocationDomain::toPresentationModel))
    }

    fun onDestroy() {
        view = null
    }
}