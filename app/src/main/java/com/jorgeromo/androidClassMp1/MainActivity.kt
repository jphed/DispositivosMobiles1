package com.jorgeromo.androidClassMp1

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.viewmodel.OnboardingViewModel
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.views.OnboardingView
import com.jorgeromo.androidClassMp1.navigation.TabBarNavigationView
import com.jorgeromo.androidClassMp1.ui.theme.AndroidClassMP1Theme
import com.jorgeromo.androidClassMp1.utils.DataStoreManager
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestNotificationPermissionIfNeeded()
        createDefaultNotificationChannelIfNeeded()
        fetchAndLogFcmToken()

        val ds = DataStoreManager(this)

        setContent {
            AndroidClassMP1Theme {
                val scope = rememberCoroutineScope()
                val vm: OnboardingViewModel = viewModel()
                val onboardingDone: Boolean? by ds.onboardingDoneFlow.collectAsState(initial = null)
                when (onboardingDone) {
                    null -> SplashLoader()
                    false -> OnboardingView(
                        viewModel = vm,
                        onFinish = {
                            scope.launch { ds.setOnboardingDone(true) }
                        }
                    )
                    true -> TabBarNavigationView()
                }
            }
        }
    }
}

private const val REQ_NOTIFICATIONS = 1001

private fun FragmentActivity.requestNotificationPermissionIfNeeded() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQ_NOTIFICATIONS
            )
        }
    }
}

private fun FragmentActivity.createDefaultNotificationChannelIfNeeded() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "default_channel"
        val name = "General"
        val descriptionText = "Notificaciones generales"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(channel)
    }
}

private fun FragmentActivity.fetchAndLogFcmToken() {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.i("FCM", "Device FCM token: $token")
        }
}

@Composable
private fun SplashLoader() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
