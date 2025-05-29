plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.bron24.bron24_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bron24.bron24_android"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

//        ndk {
//            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
//        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = false
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.androidx.games.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.morfly.compose)

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

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    implementation(libs.lifecycleViewModelCompose)

    androidTestImplementation(libs.hiltAndroidTesting)
    kaptAndroidTest(libs.hiltCompiler)

    testImplementation(libs.hiltAndroidTesting)
    kaptTest(libs.hiltCompiler)

    implementation(libs.konfetti.compose)

    //datePicker
    implementation(libs.datetimepicker)

    //orbit
    implementation(libs.orbit.core)
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.compose)

    //status bar color
    implementation(libs.accompanist.systemuicontroller)
    //motion orbital
    implementation(libs.orbital)
    implementation(libs.androidx.datastore.preferences)

    //Chuck
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)


    //voyager
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.hilt)
    implementation(libs.voyager.tabNavigator)
    implementation(libs.voyager.screenModel)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.transitions.vlatestversion)
    implementation(libs.voyager.bottomSheetNavigator)
    //yandex
    implementation(libs.maps.mobile.v431)

    debugImplementation(libs.pluto)
    releaseImplementation(libs.pluto.no.op)
    debugImplementation(libs.pluto.network)
    releaseImplementation(libs.pluto.network.no.op)
    debugImplementation(libs.pluto.network.interceptor.okhttp)
    releaseImplementation(libs.pluto.network.interceptor.okhttp.no.op)
    debugImplementation(libs.pluto.exceptions)
    releaseImplementation(libs.pluto.exceptions.no.op)
    debugImplementation(libs.pluto.logger)
    releaseImplementation(libs.pluto.logger.no.op)
    debugImplementation(libs.pluto.preferences)
    releaseImplementation(libs.pluto.preferences.no.op)
    debugImplementation(libs.pluto.layout.inspector)
    releaseImplementation(libs.pluto.layout.inspector.no.op)

    implementation(libs.timber)

    implementation(libs.androidx.material)
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}
