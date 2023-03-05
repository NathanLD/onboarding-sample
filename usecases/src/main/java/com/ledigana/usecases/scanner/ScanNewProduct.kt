package com.ledigana.usecases.scanner

import com.ledigana.domain.model.Product
import com.ledigana.domain.repository.ScannedProductsRepository

class ScanNewProduct(private val productsRepository: ScannedProductsRepository) {
    operator fun invoke(): List<Product> = productsRepository.scanNewProduct()
}