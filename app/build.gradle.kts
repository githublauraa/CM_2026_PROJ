
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // Hilt
    alias(libs.plugins.dagger)

    // Serialization
    alias(libs.plugins.kotlin.serialization)

    // KSP
    alias(libs.plugins.google.ksp)

    // Firebase
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.example.voxel_review"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.voxel_review"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }

        release {
            optimization {
                enable = false
            }
        }
    }

    packaging {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // ========================================
    // COMPOSE
    // ========================================

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // ========================================
    // ANDROIDX
    // ========================================

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Room
    implementation(libs.androidx.room.ktx)

    // ========================================
    // HILT
    // ========================================

    implementation(libs.dagger.hilt)
    implementation(libs.hilt.compose.navigation)

    // Hilt Compiler
    ksp(libs.dagger.compiler)

    // ========================================
    // FIREBASE
    // ========================================

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Storage
    implementation("com.google.firebase:firebase-storage")

    // Messaging
    implementation("com.google.firebase:firebase-messaging")

    // ========================================
    // COIL
    // ========================================

    implementation("io.coil-kt:coil-compose:2.4.0")

    // ========================================
    // RETROFIT
    // ========================================

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.retrofit2:converter-scalars:3.0.0")

    // ========================================
    // TESTS UNITARIOS
    // ========================================

    // JUnit
    testImplementation(libs.junit)

    // Mockito
    testImplementation("org.mockito:mockito-core:5.18.0")

    // Mockito Kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")

    // Coroutines Test
    testImplementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2"
    )
    androidTestImplementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2"
    )

    // Google Truth
    testImplementation("com.google.truth:truth:1.4.2")
    androidTestImplementation("com.google.truth:truth:1.4.2")


    // Kotlin Test
    testImplementation(kotlin("test"))

    // ========================================
    // TESTS INSTRUMENTADOS
    // ========================================

    // Compose Test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Espresso
    androidTestImplementation(libs.androidx.espresso.core)

    // AndroidX JUnit
    androidTestImplementation(libs.androidx.junit)

    // Hilt Android Testing
    androidTestImplementation(
        "com.google.dagger:hilt-android-testing:2.52"
    )

    // Hilt Compiler para pruebas
    kspAndroidTest(
        "com.google.dagger:hilt-compiler:2.52"
    )

    // UI Automator
    androidTestImplementation(
        "androidx.test.uiautomator:uiautomator:2.3.0"
    )

    // AndroidX Test JUnit
    androidTestImplementation(
        "androidx.test.ext:junit:1.1.5"
    )

    // ActivityScenario / AndroidX Test Core
    androidTestImplementation(
        "androidx.test:core:1.5.0"
    )

    // Mockito Android
    androidTestImplementation(
        "org.mockito:mockito-android:5.18.0"
    )

    // ========================================
    // DEBUG
    // ========================================

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
