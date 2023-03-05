package com.ledigana.onboardingsample.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ledigana.data.OnboardingRepositoryImpl
import com.ledigana.onboardingsample.R
import com.ledigana.onboardingsample.framework.onboarding.AppPreferences
import com.ledigana.usecases.onboarding.GetOnboardingViewedState
import java.io.Serializable

class OnboardingActivity: AppCompatActivity() {

    private var onboardings = mutableListOf<OnboardingType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) return

        setContentView(R.layout.onboarding_activity_single_fragment);
        onboardings = getOnboardingListFromIntent(intent).toMutableList()
        showNextOnboarding()
    }

    fun showNextOnboarding() {
        if (onboardings.isEmpty()) {
            finish()
            return
        }

        val nextOnboarding = onboardings.removeAt(0)
        val nextFragment = OnboardingFragment.newInstance(nextOnboarding)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, nextFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        (fragment as? OnboardingFragment)?.onBackPressed()
    }

    companion object {
        const val EXTRA_ONBOARDING_LIST = "extra_onboarding_list"
        const val SHARED_PREFERENCES_ONBOARDING_PREFIX = "onboarding"
        val onboardingRepository = OnboardingRepositoryImpl(AppPreferences)  // Should use Dagger for DI

        fun start(context: Context) {
            val list = createOnboardingListToShow()
            if (list.isEmpty()) return

            val intent = Intent(context, OnboardingActivity::class.java).apply {
                putExtra(EXTRA_ONBOARDING_LIST, list as Serializable)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        private fun createOnboardingListToShow(): List<OnboardingType> {
            val list = mutableListOf<OnboardingType>()

            if (!onboardingHasBeenViewed(1)) {
                list.add(OnboardingType.ONBOARDING_1)
            }
            if (!onboardingHasBeenViewed(2)) {
                list.add(OnboardingType.ONBOARDING_2)
            }

            return list
        }

        private fun onboardingHasBeenViewed(index: Int) : Boolean {
            return GetOnboardingViewedState(
                onboardingRepository,
                "${SHARED_PREFERENCES_ONBOARDING_PREFIX}_${index}"
            ).invoke()
        }

        private fun getOnboardingListFromIntent(intent: Intent): List<OnboardingType> {
            val serializable = intent.extras?.getSerializable(EXTRA_ONBOARDING_LIST)

            @Suppress("UNCHECKED_CAST")
            return serializable as List<OnboardingType>
        }
    }
}
