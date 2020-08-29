plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {

    defaultConfig {
        minSdkVersion(SDK_VERSION_MIN)
        targetSdkVersion(SDK_VERSION_TARGET)
        compileSdkVersion(SDK_VERSION_COMPILE)

        buildToolsVersion(BUILD_TOOLS_VERSION)

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // Androidx
    implementation("androidx.core:core-ktx:$androidxCoreKtxVersion")
    implementation("androidx.appcompat:appcompat:$androidxAppcompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$androidxConstraintlayoutVersion")
    implementation("androidx.startup:startup-runtime:$androidxStartupVersion")

    // Navigation
    implementation("androidx.navigation:navigation-ui-ktx:$androidxNavigationVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$androidxNavigationVersion")
    // optional - Dynamic Feature Module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$androidxNavigationVersion")
    // optional - Test helpers
    androidTestImplementation("androidx.navigation:navigation-testing:$androidxNavigationVersion")

    // Timber
    implementation("com.jakewharton.timber:timber:$timberVersion")

    // ReactiveX
    implementation("io.reactivex.rxjava3:rxjava:$rxJava3Version")
    implementation("io.reactivex.rxjava3:rxkotlin:$rxKotlin3Version")
    implementation("io.reactivex.rxjava3:rxandroid:$rxAndroid3Version")

    // Ui
    implementation("androidx.viewpager2:viewpager2:$androidxViewpager2Version")

    // Test
    testImplementation("junit:junit:$junitVersion")

    // AndroidTest
    androidTestImplementation("androidx.test.ext:junit:$androidxJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidxEspressoVersion")

}