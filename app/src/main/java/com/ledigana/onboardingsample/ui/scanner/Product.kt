package com.ledigana.onboardingsample.ui.scanner

import java.text.SimpleDateFormat
import java.util.*
import com.ledigana.domain.model.Product as ProductDomain

data class Product(val identifier: String, val date: String)

fun ProductDomain.toPresentationModel(): Product = Product(
    "ID: ${identifier}",
    date.toPrettifiedString()
)

private fun Date.toPrettifiedString(): String =
    SimpleDateFormat.getDateTimeInstance().run { format(this@toPrettifiedString) }