package com.ledigana.onboardingsample.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ledigana.data.LocationsRepositoryImpl
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.framework.location.FakeLocationSource
import com.ledigana.onboardingsample.framework.location.InMemoryLocationPersistenceSource
import com.ledigana.onboardingsample.ui.location.Location
import com.ledigana.onboardingsample.ui.location.LocationsAdapter
import com.ledigana.onboardingsample.ui.onboarding.OnboardingActivity
import com.ledigana.usecases.location.GetLocations
import com.ledigana.usecases.location.RequestNewLocation


class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val locationsAdapter = LocationsAdapter()
    private val presenter: MainPresenter

    init {
        val persistence = InMemoryLocationPersistenceSource()
        val deviceLocation = FakeLocationSource()
        val locationsRepository = LocationsRepositoryImpl(persistence, deviceLocation)
        presenter = MainPresenter(
            this,
            GetLocations(locationsRepository),
            RequestNewLocation(locationsRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Main activity
        setContentView(R.layout.activity_main)

        val recycler = findViewById(R.id.recycler) as RecyclerView
        recycler.adapter = locationsAdapter

        val newLocationBtn = findViewById(R.id.newLocationBtn) as Button
        newLocationBtn.setOnClickListener { presenter.newLocationClicked() }

        presenter.onCreate()

        // Onboardings
        val clearPreferencesBtn = findViewById(R.id.clearPreferencesBtn) as Button
        clearPreferencesBtn.setOnClickListener {
            applicationContext.getSharedPreferences("OnboardingPreferences", 0).edit().clear().commit();
            Toast.makeText(
                this, "Preferences have been cleared. You rewatch the onboardings :)",
                Toast.LENGTH_LONG
            ).show()
        }

        OnboardingActivity.start(applicationContext)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun renderLocations(locations: List<Location>) {
        locationsAdapter.items = locations
    }
}