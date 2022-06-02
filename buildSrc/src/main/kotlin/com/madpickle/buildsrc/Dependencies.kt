package com.madpickle.buildsrc

/**
 * Created by David Madilyan on 30.05.2022.
 */

object Libs{

    object Kotlin{
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.21"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21"
    }

    object Coroutines {
        private const val version = "1.6.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Android{
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val material = "com.google.android.material:material:1.6.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

        object Lifecycle {
            private const val version = "2.4.1"
            const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val service = "androidx.lifecycle:lifecycle-service:$version"
        }

        object DI {
            private const val version = "2.38.1"
            const val hiltKapt = "com.google.dagger:hilt-compiler:$version"
            const val hiltAndroid = "com.google.dagger:hilt-android:$version"
            const val hiltFragment = "androidx.hilt:hilt-navigation-fragment:1.0.0"
        }

        object Navigation {
            private const val version = "2.4.0"
            const val navFragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val navUi =  "androidx.navigation:navigation-ui-ktx:$version"
            const val navDynamic = "androidx.navigation:navigation-dynamic-features-fragment:$version"
        }

        object Room {
            private const val version = "2.4.2"
            const val ktx = "androidx.room:room-ktx:$version"
            const val roomKapt = "androidx.room:room-compiler:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val annotationProcessor = "androidx.room:room-compiler:$version"
            const val paging = "androidx.room:room-paging:2.5.0-alpha01"
        }

        object Service{
            private const val version = "2.9.0"
            private const val xml_version = "0.8.13"
            const val lib = "com.squareup.retrofit2:retrofit:$version"
//            const val xmlConverter = "com.tickaroo.tikxml:retrofit-converter:$xml_version"
//            const val xmlCore = "com.tickaroo.tikxml:core:$xml_version"
//            const val xmlAnnotation = "com.tickaroo.tikxml:processor:$xml_version"
            const val jsonParser = "com.squareup.retrofit2:converter-gson:$version"
            const val converter = "com.squareup.retrofit2:converter-scalars:$version"
            const val okHttp = "com.squareup.okhttp3:okhttp:4.9.3"
            const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:4.7.2"
        }

        object Paging{
            private const val version = "3.1.1"
            const val runtime = "androidx.paging:paging-runtime:$version"
            const val guava = "androidx.paging:paging-guava:$version"
        }
    }

    object Coil {
        const val coilKt = "io.coil-kt:coil:1.3.2"
    }

    object Truth{
        const val truthTest = "com.google.truth:truth:1.1.3"
    }

    object Logs {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

}