plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.buntupana.tmdb.app"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.buntupana.tmdb"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    coreLibraryDesugaring(libs.desugar.jdk)

    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":feature:account:di"))
    implementation(project(":feature:account:presentation"))
    implementation(project(":feature:account:domain"))
    implementation(project(":feature:discover:di"))
    implementation(project(":feature:discover:presentation"))
    implementation(project(":feature:discover:domain"))
    implementation(project(":feature:detail:di"))
    implementation(project(":feature:detail:presentation"))
    implementation(project(":feature:search:di"))
    implementation(project(":feature:search:presentation"))
    implementation(project(":feature:lists:di"))
    implementation(project(":feature:lists:presentation"))

    implementation(libs.kotlinx.serialization.json)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.activity.compose)

    // Material
    implementation(libs.androidx.material3)

    // Tools
    implementation(libs.androidx.browser)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
}