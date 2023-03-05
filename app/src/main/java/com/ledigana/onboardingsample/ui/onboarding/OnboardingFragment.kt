package com.ledigana.onboardingsample.ui.onboarding

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ledigana.data.OnboardingRepositoryImpl
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.framework.onboarding.AppPreferences
import com.ledigana.onboardingsample.ui.serializable
import com.ledigana.usecases.onboarding.SetOnboardingAsViewed

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

class OnboardingFragment : Fragment() {

    private val onboardingRepository = OnboardingRepositoryImpl(AppPreferences)  // Should use Dagger for DI

    private lateinit var type: OnboardingType

    private lateinit var closeImageView: ImageView
    private lateinit var onboardingCountDots: LinearLayout
    private var dotsCount: Int = 0
    private lateinit var dots: Array<ImageView?>

    private lateinit var onboardingPager: ViewPager
    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var onboardingButton: Button
    private var onboardingItems: ArrayList<OnboardingItem> = ArrayList()

    companion object {
        const val ARG_ONBOARDING_TYPE = "ARG_ONBOARDING_TYPE"

        fun newInstance(type: OnboardingType): OnboardingFragment {
            val onboardingFragment = OnboardingFragment()

            val args = Bundle().apply {
                putSerializable(ARG_ONBOARDING_TYPE, type)
            }
            onboardingFragment.setArguments(args)

            return onboardingFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onboardingType = requireArguments().serializable<OnboardingType>(ARG_ONBOARDING_TYPE)
        when (onboardingType) {
            null -> finishOnboarding()
            else -> type = onboardingType
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_onboarding_holder, container, false)

        onboardingButton = view.findViewById(R.id.viewPagerButton)
        onboardingPager = view.findViewById(R.id.viewPagerContent)
        onboardingCountDots = view.findViewById(R.id.viewPagerCountDots)
        closeImageView = view.findViewById(R.id.quitImageView)

        loadData()

        onboardingAdapter = OnboardingAdapter(requireContext(), onboardingItems)
        onboardingPager.apply {
            adapter = onboardingAdapter
            currentItem = 0
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) { }

                override fun onPageSelected(position: Int) {

                    for (i in 0 until dotsCount) {
                        dots[i]?.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.non_selected_item_dot
                            )
                        )
                    }

                    dots[position]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.selected_item_dot
                        )
                    )

                    val pos = position + 1
                    onboardingButton.text = if (pos == dotsCount) getString(R.string.common_action_finish) else getString(R.string.common_next)
                }

                override fun onPageScrollStateChanged(state: Int) { }
            })
        }

        closeImageView.setOnClickListener {
            finishOnboarding()
        }

        onboardingButton.setOnClickListener {
            when (val currentPage = onboardingPager.currentItem + 1) {
                dotsCount -> finishOnboarding()
                else -> onboardingPager.setCurrentItem(currentPage, true)
            }
        }

        onboardingButton.text = if (dotsCount > 1) getString(R.string.common_next) else getString(R.string.common_action_finish)

        createDotsUI()

        return view
    }

    private fun loadData() {
        when (type) {
            OnboardingType.ONBOARDING_1 -> loadOnboarding1()
            OnboardingType.ONBOARDING_2 -> loadOnboarding2()
        }

        dotsCount = onboardingItems.size
    }

    private fun loadOnboarding1() {
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_1,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_2,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_3,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
    }

    private fun loadOnboarding2() {
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_1,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_1,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_2,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
        onboardingItems.add(OnboardingItem(
                R.drawable.onboarding_3,
                getString(R.string.onboarding_1_title_1),
                getString(R.string.onboarding_1_description_1)
        ))
    }

    private fun createDotsUI() {
        dotsCount = onboardingAdapter.count
        dots = arrayOfNulls(dotsCount)

        for (i in 0 until dotsCount) {
            dots[i] = ImageView(context)
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.non_selected_item_dot
                )
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(2.toPx(), 0, 2.toPx(), 0)

            onboardingCountDots.addView(dots[i], params)
        }

        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.selected_item_dot
            )
        )
    }

    private fun finishOnboarding() {
        when (type) {
            OnboardingType.ONBOARDING_1 -> setOnboardingAsViewed(1)
            OnboardingType.ONBOARDING_2 -> setOnboardingAsViewed(2)
        }
        val onboardingActivity = activity as OnboardingActivity
        onboardingActivity.showNextOnboarding()
    }

    private fun setOnboardingAsViewed(index: Int) {
        return SetOnboardingAsViewed(
            onboardingRepository,
            "${OnboardingActivity.SHARED_PREFERENCES_ONBOARDING_PREFIX}_${index}"
        ).invoke()
    }

    fun onBackPressed(): Boolean {
        if (onboardingPager.currentItem > 0) {
            onboardingPager.setCurrentItem(onboardingPager.currentItem - 1, true)
        } else {
            finishOnboarding()
        }
        return true
    }
}
