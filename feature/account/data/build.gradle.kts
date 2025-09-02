plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.buntupana.tmdb.feature.account.data"
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
}

dependencies {

    coreLibraryDesugaring(libs.desugar.jdk)

    // Modules
    implementation(project(":feature:account:domain"))
    implementation(project(":core:data"))

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Networking
    implementation(platform(libs.io.ktor.bom))
    implementation(libs.bundles.ktor)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Tools
    api(libs.jakewharton.timber)
    implementation(libs.squareup.moshi)
    ksp(libs.squareup.moshi.ksp)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}