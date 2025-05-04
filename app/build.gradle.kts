plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.tranxortrider.deliveryrider"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tranxortrider.deliveryrider"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    
    // CircleImageView for circular profile images
    implementation("de.hdodenhof:circleimageview:3.1.0")
    
    // Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    
    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10")
    
    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // OSMDroid for Maps
    implementation("org.osmdroid:osmdroid-android:6.1.16")
    implementation("org.osmdroid:osmdroid-mapsforge:6.1.16")
    
    // Play Services Location for getting device location
    implementation("com.google.android.gms:play-services-location:21.0.1")
    
    // Google Maps Utils for polyline decoding and other utilities
    implementation("com.google.maps.android:android-maps-utils:3.4.0")
    
    // MPAndroidChart for visualization
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    
    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    
    // CameraX
    implementation("androidx.camera:camera-core:1.3.2")
    implementation("androidx.camera:camera-camera2:1.3.2")
    implementation("androidx.camera:camera-lifecycle:1.3.2")
    implementation("androidx.camera:camera-view:1.3.2")
    implementation("androidx.camera:camera-extensions:1.3.2")
    
    // ML Kit for barcode scanning
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    
    // SwipeRefreshLayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore")
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))

    // Firebase Analytics
    implementation("com.google.firebase:firebase-analytics")
    
    // Firebase Cloud Messaging for push notifications
    implementation("com.google.firebase:firebase-messaging")
    
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")
    
    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database")
    
    // Firebase Storage
    implementation("com.google.firebase:firebase-storage")
    
    // Firebase Cloud Functions
    implementation("com.google.firebase:firebase-functions")
    
    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Supabase dependencies
    implementation("io.github.jan-tennert.supabase:storage-kt:2.2.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.2.1")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.2.1")
    implementation("io.ktor:ktor-client-android:2.3.7")
}