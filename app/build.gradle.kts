plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.pokemondirectory"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pokemondirectory"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.pokemondirectory.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Kotlin Standard Library
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room components
    implementation(libs.androidx.room.runtime) // Use the latest version
    annotationProcessor(libs.androidx.room.compiler) // For Java projects
    kapt(libs.androidx.room.compiler)

    // Optional - Kotlin extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.hilt.android.v2511)
    kapt (libs.hilt.android.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.gson) // For JSON parsing

    implementation(libs.androidx.paging.compose) // Latest stable version
    implementation(libs.androidx.paging.runtime)         // Paging core runtime
    implementation(libs.androidx.hilt.navigation.compose)

    implementation (libs.glide.v4151) // Add the latest version of Glide
    kapt(libs.compiler.v4151)
    implementation (libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.androidx.junit.v113)
    testImplementation(libs.androidx.espresso.core.v340)
    testImplementation(libs.mockito.inline)

    // Espresso for UI testing
    androidTestImplementation(libs.androidx.espresso.core.v361)
    androidTestImplementation(libs.androidx.espresso.intents)

    // Hilt Testing
    androidTestImplementation(libs.androidx.hilt.navigation.compose.v100)
    androidTestImplementation(libs.hilt.android.testing.v2405)
    androidTestImplementation(libs.androidx.junit.v121)

    // Coroutine testing
    androidTestImplementation(libs.kotlinx.coroutines.test.v160)

    testImplementation (libs.mockito.core.v461) // Mockito core library for mocking

    // Mockito Kotlin for easier Kotlin support
    testImplementation (libs.mockito.kotlin.v400)
    androidTestImplementation (libs.ui.test.junit4)
    debugImplementation (libs.ui.test.manifest)

    // Hilt for testing
    androidTestImplementation (libs.androidx.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing.v2405)

    // JUnit for testing
    androidTestImplementation (libs.androidx.junit.v121)
    implementation (libs.androidx.hilt.work)

    implementation (libs.hilt.android.v2405)
    //kapt( libs.hilt.compiler.v2405)

    // Hilt testing dependencies for instrumentation tests
    androidTestImplementation (libs.hilt.android.testing.v2405)
    androidTestImplementation (libs.androidx.hilt.navigation.compose)
    androidTestImplementation (libs.androidx.junit.v121)
    androidTestImplementation (libs.androidx.ui.test.junit4.v105)

    // Coroutine testing dependencies
    androidTestImplementation (libs.kotlinx.coroutines.test.v160)
    androidTestImplementation (libs.hilt.android.testing.v244)
}