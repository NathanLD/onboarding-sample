package com.ledigana.data

import com.ledigana.domain.model.Product
import com.ledigana.domain.repository.ScannedProductsRepository

class ScannedProductsRepositoryImpl(
    private val productsPersistenceSource: ScannedProductsPersistenceSource,
    private val deviceScanner: DeviceScanner
): ScannedProductsRepository {

    override fun getSavedProducts(): List<Product> = productsPersistenceSource.getPersistedProducts()

    override fun scanNewProduct(): List<Product> {
        val newProduct = deviceScanner.scanProduct()
        productsPersistenceSource.saveNewProduct(newProduct)
        return getSavedProducts()
    }

}

interface ScannedProductsPersistenceSource {
    fun getPersistedProducts(): List<Product>
    fun saveNewProduct(product: Product)
}

interface DeviceScanner {
    fun scanProduct(): Product
}