plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.buntupana.tmdb.core.di"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    compilerOptions {
        languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Networking
    implementation(platform(libs.io.ktor.bom))
    implementation(libs.bundles.ktor)

    // Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.common)
    ksp(libs.androidx.room.compiler)

    // Tools
    implementation(libs.squareup.moshi)
    ksp(libs.squareup.moshi.ksp)

    // Testing
    testImplementation(libs.junit)
}