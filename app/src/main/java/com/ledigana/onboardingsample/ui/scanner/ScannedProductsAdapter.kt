package com.ledigana.onboardingsample.ui.scanner

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.ui.inflate

class ScannedProductsAdapter : RecyclerView.Adapter<ScannedProductsAdapter.ViewHolder>() {

    var items: List<Product> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.view_product_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productIdentifierTV: TextView
        val productDateTV: TextView

        init {
            productIdentifierTV = view.findViewById(R.id.productIdentifier)
            productDateTV = view.findViewById(R.id.productDate)
        }

        fun bind(product: Product) {
            productIdentifierTV.text = product.identifier
            productDateTV.text = product.date
        }
    }
}