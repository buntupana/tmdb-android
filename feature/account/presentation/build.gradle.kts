plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.buntupana.tmdb.feature.account.presentation"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    coreLibraryDesugaring(libs.desugar.jdk)

    // Modules
    implementation(project(":core:ui"))
    implementation(project(":feature:account:domain"))
    implementation(project(":feature:lists:domain"))

    // Kotlin
    implementation(libs.kotlinx.serialization.json)

    // Dagger Hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.ksp)
    api(libs.dagger.hilt.navigation.compose)
    api(libs.dagger.hilt.navigation.compose)

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation)

    // Material
    implementation(libs.androidx.material3)

    // Tools
    implementation(libs.jakewharton.timber)
    implementation(libs.io.coil.kt)
    implementation(libs.androidx.pallete)


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}