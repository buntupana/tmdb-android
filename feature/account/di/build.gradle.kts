plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.buntupana.tmdb.feature.account.di"
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

    implementation(project(":feature:account:presentation"))
    implementation(project(":feature:account:domain"))
    implementation(project(":feature:lists:domain"))
    implementation(project(":feature:account:data"))
    implementation(project(":core:data"))
    implementation(project(":core:di"))

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Room
//    implementation(libs.androidx.room)
//    implementation(libs.androidx.room.common)
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)

    // Networking
    implementation(platform(libs.io.ktor.bom))
    implementation(libs.bundles.ktor)

    // Testing
    testImplementation(libs.junit)
}