package com.ledigana.onboardingsample.ui.location

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.ui.inflate

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    var items: List<Location> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.view_location_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationCoordinatesTV: TextView
        val locationDateTV: TextView

        init {
            locationCoordinatesTV = view.findViewById(R.id.locationCoordinates)
            locationDateTV = view.findViewById(R.id.locationDate)
        }

        fun bind(location: Location) {
            locationCoordinatesTV.text = location.coordinates
            locationDateTV.text = location.date
        }
    }
}