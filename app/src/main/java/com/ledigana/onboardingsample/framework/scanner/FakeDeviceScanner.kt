package com.ledigana.onboardingsample.framework.scanner

import com.ledigana.data.DeviceScanner
import com.ledigana.domain.model.Product
import java.util.*

class FakeDeviceScanner : DeviceScanner {

    private val random = Random(System.currentTimeMillis())

    override fun scanProduct(): Product =
        Product(10000 + random.nextLong() * 89999, Date())
}