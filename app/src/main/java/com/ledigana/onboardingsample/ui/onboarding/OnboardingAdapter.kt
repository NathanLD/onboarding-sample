package com.ledigana.onboardingsample.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.ledigana.onboardingsample.R

class OnboardingAdapter(
    val context: Context,
    val items: List<OnboardingItem>
): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.fragment_onboarding_item, container, false)
        val item = items[position]

        val imageView = itemView.findViewById<ImageView>(R.id.onboarding_main_image)
        imageView.setImageResource(item.imageId)

        val titleTextView = itemView.findViewById<TextView>(R.id.onboarding_title_textview)
        titleTextView.text = item.title

        val contentTextView = itemView.findViewById<TextView>(R.id.onboarding_description_textview)
        contentTextView.text = item.description

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
