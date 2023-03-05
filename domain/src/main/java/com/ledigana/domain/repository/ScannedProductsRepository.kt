package com.ledigana.domain.repository

import com.ledigana.domain.model.Product

interface ScannedProductsRepository {
    fun getSavedProducts(): List<Product>
    fun scanNewProduct(): List<Product>
}