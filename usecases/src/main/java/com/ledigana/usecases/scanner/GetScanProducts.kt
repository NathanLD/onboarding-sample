package com.ledigana.usecases.scanner

import com.ledigana.domain.model.Product
import com.ledigana.domain.repository.ScannedProductsRepository

class GetScanProducts(private val productsRepository: ScannedProductsRepository) {
    operator fun invoke(): List<Product> = productsRepository.getSavedProducts()
}