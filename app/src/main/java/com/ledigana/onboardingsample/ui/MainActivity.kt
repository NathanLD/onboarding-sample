package com.ledigana.onboardingsample.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ledigana.data.LocationsRepository
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.framework.location.FakeLocationSource
import com.ledigana.onboardingsample.framework.location.InMemoryLocationPersistenceSource
import com.ledigana.onboardingsample.ui.location.Location
import com.ledigana.onboardingsample.ui.location.LocationsAdapter
import com.ledigana.usecases.GetLocations
import com.ledigana.usecases.RequestNewLocation


class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val locationsAdapter = LocationsAdapter()
    private val presenter: MainPresenter

    init {
        // This would be done by a dependency injector in a complex App
        //
        val persistence = InMemoryLocationPersistenceSource()
        val deviceLocation = FakeLocationSource()
        val locationsRepository = LocationsRepository(persistence, deviceLocation)
        presenter = MainPresenter(
            this,
            GetLocations(locationsRepository),
            RequestNewLocation(locationsRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById(R.id.recycler) as RecyclerView
        recycler.adapter = locationsAdapter

        val newLocationBtn = findViewById(R.id.newLocationBtn) as Button
        newLocationBtn.setOnClickListener { presenter.newLocationClicked() }

        presenter.onCreate()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun renderLocations(locations: List<Location>) {
        locationsAdapter.items = locations
    }
}