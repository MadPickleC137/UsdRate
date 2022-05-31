import com.madpickle.buildsrc.Libs

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = com.madpickle.buildsrc.Config.appId
        minSdk = 27
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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

    viewBinding {
        android.buildFeatures.viewBinding = true
    }
    android {
        splits {
            abi {
                isEnable = true
                reset()
                include ("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            }
        }
    }
}

dependencies {
    kapt (Libs.Android.DI.hiltKapt)
    kapt (Libs.Android.Room.roomKapt)

    annotationProcessor (Libs.Android.Room.annotationProcessor)

    implementation( Libs.Android.DI.hiltAndroid)
    implementation (Libs.Android.DI.hiltFragment)
    implementation (Libs.Android.Lifecycle.liveData)
    implementation (Libs.Android.Lifecycle.savedState)
    implementation (Libs.Android.Lifecycle.viewModel)
    implementation (Libs.Android.Lifecycle.service)
    implementation (Libs.Android.Navigation.navFragment)
    implementation (Libs.Android.Navigation.navDynamic)
    implementation (Libs.Android.Navigation.navUi)
    implementation (Libs.Android.Room.ktx)
    implementation (Libs.Android.Room.paging)
    implementation (Libs.Android.Room.runtime)
    implementation (Libs.Android.Retrofit.jsonParser)
    implementation (Libs.Android.Retrofit.xmlParser)
    implementation (Libs.Android.Retrofit.okHttp)
    implementation (Libs.Android.Retrofit.lib)
    implementation (Libs.Android.Paging.runtime)
    implementation (Libs.Android.Paging.guava)

    implementation (Libs.Android.core)
    implementation (Libs.Android.appcompat)
    implementation (Libs.Android.material)
    implementation (Libs.Android.constraintLayout)
    implementation (Libs.Android.constraintLayout)

    implementation (Libs.Coroutines.android)
    implementation (Libs.Coroutines.core)
    implementation (Libs.Kotlin.stdlib)




    //test
    testImplementation (Libs.Coroutines.test)
    testImplementation (Libs.Android.junit)
    androidTestImplementation (Libs.Android.junitExt)
    androidTestImplementation (Libs.Android.espresso)
}