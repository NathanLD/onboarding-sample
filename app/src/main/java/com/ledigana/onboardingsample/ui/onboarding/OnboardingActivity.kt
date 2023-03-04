package com.ledigana.onboardingsample.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ledigana.onboardingsample.R
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

        fun start(context: Context) {
            val list = createOnboardingListToShow()
            if (list.isEmpty()) return

            val intent = Intent(context, OnboardingActivity::class.java).apply {
                putExtra(EXTRA_ONBOARDING_LIST, list as Serializable)
            }
            context.startActivity(intent)
        }

        private fun createOnboardingListToShow(): List<OnboardingType> {
            val list = mutableListOf<OnboardingType>()

            // TODO: Vérifier dans les préférences si l'Onboarding a déjà été vu
            if (true) {
                list.add(OnboardingType.ONBOARDING_1)
            }
            if (true) {
                list.add(OnboardingType.ONBOARDING_2)
            }

            return list
        }

        private fun getOnboardingListFromIntent(intent: Intent): List<OnboardingType> {
            val serializable = intent.extras?.getStringArrayList(EXTRA_ONBOARDING_LIST)?.map { name -> OnboardingType.valueOf(name) }
            return serializable ?: ArrayList<OnboardingType>()
        }
    }
}
