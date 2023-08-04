import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.anshtya.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anshtya.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val key = gradleLocalProperties(rootDir).getProperty("API_KEY") ?: ""
        buildConfigField("String", "API_KEY", "\"$key\"")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
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
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        kotlinOptions {
            jvmTarget = "17"
        }

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.3"
        }

        packaging {
            resources {
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }

        kapt {
            correctErrorTypes = true
        }
    }

    dependencies {

        implementation("androidx.core:core-ktx:1.10.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation("androidx.compose.ui:ui:1.4.3")
        implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
        implementation("androidx.compose.material3:material3:1.1.1")
        testImplementation("junit:junit:4.13.2")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
        debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
        debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.3")

        //Lifecycle
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

        //DataStore
        implementation("androidx.datastore:datastore-preferences:1.0.0")

        //Navigation
        implementation("androidx.navigation:navigation-compose:2.6.0")
        implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

        //Room
        implementation("androidx.room:room-runtime:2.5.2")
        implementation("androidx.room:room-ktx:2.5.2")
        ksp("androidx.room:room-compiler:2.5.2")

        //Hilt
        implementation("com.google.dagger:hilt-android:2.44")
        kapt("com.google.dagger:hilt-compiler:2.44")

        //Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.4.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")

        //Location Service
        implementation("com.google.android.gms:play-services-location:21.0.1")

        //Lottie
        implementation("com.airbnb.android:lottie-compose:6.1.0")
    }
}