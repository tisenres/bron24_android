plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.bron24.bron24_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bron24.bron24_android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    kapt(libs.hiltCompilerAndroidX)
    implementation(libs.hiltNavigationCompose)

    implementation(libs.locationServices)
    implementation(libs.coil)
    implementation(libs.valentinilkShimmer)
    implementation(libs.yandex.maps)
    implementation(libs.maps.compose)

    implementation(libs.retrofit)
    implementation(libs.retrofitMoshi)
    implementation(libs.converterGson)
    implementation(libs.okhttpLoggingInterceptor)

    implementation(libs.lifecycleViewModelCompose)

    androidTestImplementation(libs.hiltAndroidTesting)
    kaptAndroidTest(libs.hiltCompiler)

    testImplementation(libs.hiltAndroidTesting)
    kaptTest(libs.hiltCompiler)
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}
