package com.ledigana.onboardingsample.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ledigana.onboardingsample.ui.onboarding.OnboardingActivity
import com.ledigana.data.LocationsRepository
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.framework.location.FakeLocationSource
import com.ledigana.onboardingsample.framework.location.InMemoryLocationPersistenceSource
import com.ledigana.onboardingsample.ui.location.Location
import com.ledigana.onboardingsample.ui.location.LocationsAdapter
import com.ledigana.onboardingsample.ui.onboarding.OnboardingType
import com.ledigana.usecases.GetLocations
import com.ledigana.usecases.RequestNewLocation

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val locationsAdapter = LocationsAdapter()
    private val presenter: MainPresenter
    private val EXTRA_ONBOARDING = "extra_onboarding"

    init {
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

        // Main activity
        setContentView(R.layout.activity_main)

        val recycler = findViewById(R.id.recycler) as RecyclerView
        recycler.adapter = locationsAdapter

        val newLocationBtn = findViewById(R.id.newLocationBtn) as Button
        newLocationBtn.setOnClickListener { presenter.newLocationClicked() }

        presenter.onCreate()

        // Onboardings
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(EXTRA_ONBOARDING, true)
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_ONBOARDING)) {
            var onboardingIntent = Intent(this, OnboardingActivity::class.java)
            val types = arrayListOf(OnboardingType.ONBOARDING_1.name, OnboardingType.ONBOARDING_2.name)
            onboardingIntent.putStringArrayListExtra(OnboardingActivity.EXTRA_ONBOARDING_LIST, types)
            startActivity(onboardingIntent)
        }
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun renderLocations(locations: List<Location>) {
        locationsAdapter.items = locations
    }
}