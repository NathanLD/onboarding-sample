package com.ledigana.onboardingsample.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ledigana.data.ScannedProductsRepositoryImpl
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.framework.scanner.FakeDeviceScanner
import com.ledigana.onboardingsample.framework.scanner.InMemoryScannedProductsPersistenceSource
import com.ledigana.onboardingsample.ui.scanner.Product
import com.ledigana.onboardingsample.ui.scanner.ScannedProductsAdapter
import com.ledigana.onboardingsample.ui.onboarding.OnboardingActivity
import com.ledigana.usecases.scanner.GetScanProducts
import com.ledigana.usecases.scanner.ScanNewProduct


class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val productsAdapter = ScannedProductsAdapter()
    private val presenter: MainPresenter

    init {
        val persistence = InMemoryScannedProductsPersistenceSource()
        val deviceScanner = FakeDeviceScanner()
        val productsRepository = ScannedProductsRepositoryImpl(persistence, deviceScanner)
        presenter = MainPresenter(
            this,
            GetScanProducts(productsRepository),
            ScanNewProduct(productsRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Main activity
        setContentView(R.layout.activity_main)

        val recycler = findViewById(R.id.recycler) as RecyclerView
        recycler.adapter = productsAdapter

        val scanProductBtn = findViewById(R.id.scanProductBtn) as Button
        scanProductBtn.setOnClickListener { presenter.scanProductClicked() }

        presenter.onCreate()

        // Onboardings
        val clearPreferencesBtn = findViewById(R.id.clearPreferencesBtn) as Button
        clearPreferencesBtn.setOnClickListener {
            applicationContext.getSharedPreferences("OnboardingPreferences", 0).edit().clear().commit();
            Toast.makeText(
                this, "Preferences cleared. You can now rewatch the onboardings :)",
                Toast.LENGTH_LONG
            ).show()
        }

        OnboardingActivity.start(applicationContext)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun renderProducts(products: List<Product>) {
        productsAdapter.items = products
    }
}