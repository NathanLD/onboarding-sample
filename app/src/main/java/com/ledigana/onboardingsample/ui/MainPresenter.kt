package com.ledigana.onboardingsample.ui

import com.ledigana.onboardingsample.ui.scanner.Product
import com.ledigana.onboardingsample.ui.scanner.toPresentationModel
import com.ledigana.domain.model.Product as ProductDomain
import com.ledigana.usecases.scanner.GetScanProducts
import com.ledigana.usecases.scanner.ScanNewProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(
    private var view: View?,
    private val getProducts: GetScanProducts,
    private val scanNewProduct: ScanNewProduct
) {
    interface View {
        fun renderProducts(products: List<Product>)
    }

    fun onCreate() = GlobalScope.launch(Dispatchers.Main) {
        val products = withContext(Dispatchers.IO) { getProducts() }
        view?.renderProducts(products.map(ProductDomain::toPresentationModel))
    }

    fun scanProductClicked() = GlobalScope.launch(Dispatchers.Main) {
        val products = withContext(Dispatchers.IO) { scanNewProduct() }
        view?.renderProducts(products.map(ProductDomain::toPresentationModel))
    }

    fun onDestroy() {
        view = null
    }
}