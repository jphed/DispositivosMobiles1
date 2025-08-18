package com.jorgeromo.androidClassMp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.views.OnboardingView
import com.jorgeromo.androidClassMp1.ui.theme.AndroidClassMP1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.viewmodel.OnboardingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidClassMP1Theme {
                val vm: OnboardingViewModel = viewModel()
                OnboardingView(viewModel = vm)
                // TabBarNavigationView()
            }
        }
    }
}

