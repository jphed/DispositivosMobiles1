plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.jorgeromo.androidClassMp1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jorgeromo.androidClassMp1"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.airbnb.android:lottie-compose:6.0.1")
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // BOM de Compose
    implementation(platform(libs.androidx.compose.bom))

    // Artefactos Compose (sin versión explícita; los controla el BOM)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navegación (deja una sola versión)
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Icons extendidos (sin versión, gobernado por el BOM)
    implementation("androidx.compose.material:material-icons-extended")

    // ViewModel para Compose (recomendado 2.8.x)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Networking (no requerido para el onboarding)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.4")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Pager moderno (HorizontalPager / rememberPagerState)
    implementation("androidx.compose.foundation:foundation")

    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.compose.material3:material3:1.2.1")

    // CameraX para preview, analyzer y lifecycle
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    // ML Kit para escaneo de códigos de barras (QR)
    implementation("com.google.mlkit:barcode-scanning:17.0.3")
    implementation("com.google.mlkit:vision-common:17.3.0")

    // Coroutines (StateFlow / flow)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}