plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
//    id("com.android.library") version "8.1.1" apply false
//    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

//plugins {
//    id("com.google.dagger.hilt.android")
//    id("kotlin-kapt")
//}

android {
    namespace = "com.example.nationalizeapiandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nationalizeapiandroid"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit and GSON dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

// Navigation Component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.2.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.2.2")

    // Room components
    implementation ("androidx.room:room-runtime:2.2.5")
//    implementation(libs.androidx.room.runtime)
//    annotationProcessor(libs.androidx.room.compiler)
//    ksp(libs.androidx.room.compiler)
//    implementation (libs.kotlin.scripting.compiler.embeddable)
//    implementation(libs.androidx.room.ktx)
    kapt ("androidx.room:room-compiler:2.2.5")

//    implementation ("androidx.room:room-ktx:2.2.5")
    implementation ("androidx.room:room-ktx:2.4.1")
    androidTestImplementation ("androidx.room:room-testing:2.2.5")

    // Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    // Kotlin components
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")

    // Якщо використовуєте Kotlin Coroutines:


    implementation("androidx.recyclerview:recyclerview:1.2.1")

}
