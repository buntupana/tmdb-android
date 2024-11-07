plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.secrets.gradle)
}

android {
    namespace = "com.buntupana.tmdb.core"
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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    coreLibraryDesugaring(libs.desugar.jdk)

    // Kotlin
    implementation(libs.kotlinx.serialization.json)

    // Android
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    debugApi(libs.androidx.ui.tooling)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.navigation)
    api(libs.androidx.paging.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.icons.extended)

    // Accompanist
    api(libs.google.accompanist.systemuicontroller)

    // Navigation

    // Material
    api(libs.androidx.material3)

    // Networking
    api(libs.squareup.retrofit2.retrofit)
    api(libs.squareup.retrofit2.converter.moshi)
    api(libs.squareup.okhttp3.okhttp)
    api(libs.squareup.okhttp3.login.interceptor)

    // Dagger Hilt
    api(libs.dagger.hilt)
    ksp(libs.dagger.hilt.ksp)
    api(libs.dagger.hilt.navigation.compose)

    // Tools
    api(libs.jakewharton.timber)
    api(libs.io.coil.kt)
    api(libs.androidx.pallete)
    implementation(libs.squareup.moshi)
    ksp(libs.squareup.moshi.ksp)

    // Testing
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
//    debugImplementation "androidx.compose.ui:ui-tooling:$compose_ui_version"
//    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_ui_version"
}