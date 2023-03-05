package com.ledigana.onboardingsample.framework.scanner

import com.ledigana.data.ScannedProductsPersistenceSource
import com.ledigana.domain.model.Product

class InMemoryScannedProductsPersistenceSource : ScannedProductsPersistenceSource {

    private var products: List<Product> = emptyList()

    override fun getPersistedProducts(): List<Product> = products

    override fun saveNewProduct(product: Product) {
        products = products + product
    }
}