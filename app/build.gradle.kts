plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.md.testnews"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.md.testnews"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField(type = "String", name = "API_KEY", value = "\"0d3f7f6deb6e4754b37207e8be3626e7\"")
            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://newsapi.org/v2/\"")
            buildConfigField(type = "String", name = "NEWS_SOURCE", value = "\"bbc-news\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(type = "String", name = "API_KEY", value = "\"0d3f7f6deb6e4754b37207e8be3626e7\"")
            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://newsapi.org/v2/\"")
            buildConfigField(type = "String", name = "NEWS_SOURCE", value = "\"bbc-news\"")
        }
        create("reutersDebug") {
            initWith(getByName("debug"))
            manifestPlaceholders["hostName"] = "com.md.testnews.reuters"
            applicationIdSuffix = ".reuters"
            buildConfigField(type = "String", name = "API_KEY", value = "\"0d3f7f6deb6e4754b37207e8be3626e7\"")
            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://newsapi.org/v2/\"")
            buildConfigField(type = "String", name = "NEWS_SOURCE", value = "\"reuters\"")
        }
        create("reutersRelease") {
            initWith(getByName("release"))
            manifestPlaceholders["hostName"] = "com.md.testnews.reuters"
            applicationIdSuffix = ".reuters"
            buildConfigField(type = "String", name = "API_KEY", value = "\"0d3f7f6deb6e4754b37207e8be3626e7\"")
            buildConfigField(type = "String", name = "BASE_URL", value = "\"https://newsapi.org/v2/\"")
            buildConfigField(type = "String", name = "NEWS_SOURCE", value = "\"reuters\"")
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.compose.navigation)
    implementation(libs.kotlin.serialization)
    implementation(libs.biometric.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.kotlin.json)
    implementation(libs.room.core)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.coil)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.coroutines.testcore)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}