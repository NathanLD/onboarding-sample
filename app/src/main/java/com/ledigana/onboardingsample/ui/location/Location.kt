package com.ledigana.onboardingsample.ui.location

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import com.ledigana.domain.Location as LocationDomain

data class Location(val coordinates: String, val date: String)

fun LocationDomain.toPresentationModel(): Location = Location(
    "${latitude.toPrettifiedString()} | ${longitude.toPrettifiedString()}",
    date.toPrettifiedString()
)

private fun Date.toPrettifiedString(): String =
    SimpleDateFormat.getDateTimeInstance().run { format(this@toPrettifiedString) }

private fun Double.toPrettifiedString(): String {

    val df = DecimalFormat("#.###")
    df.roundingMode = RoundingMode.CEILING

    return df.format(this)

}