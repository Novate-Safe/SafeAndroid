plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    defaultConfig {
        applicationId = "net.novate.safe"

        minSdkVersion(SDK_VERSION_MIN)
        targetSdkVersion(SDK_VERSION_TARGET)
        compileSdkVersion(SDK_VERSION_COMPILE)

        buildToolsVersion(BUILD_TOOLS_VERSION)

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(":base"))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // Androidx
    implementation("androidx.appcompat:appcompat:$androidxAppcompatVersion")

    // Androidx Ktx
    implementation("androidx.core:core-ktx:$androidxCoreKtxVersion")
    implementation("androidx.fragment:fragment-ktx:$androidxFragmentKtxVersion")
    implementation("androidx.collection:collection-ktx:$androidxCollectionKtxVersion")
    implementation("androidx.startup:startup-runtime:$androidxStartupVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$androidxLifecycleVersion")
    // annotation processor
    implementation("androidx.lifecycle:lifecycle-common-java8:$androidxLifecycleVersion")
    // optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:$androidxLifecycleVersion")
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-process:$androidxLifecycleVersion")
    // optional - ReactiveStreams support for LiveData
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$androidxLifecycleVersion")

    // Navigation
    implementation("androidx.navigation:navigation-ui-ktx:$androidxNavigationVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$androidxNavigationVersion")
    // optional - Dynamic Feature Module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$androidxNavigationVersion")
    // optional - Test helpers
    androidTestImplementation("androidx.navigation:navigation-testing:$androidxNavigationVersion")

    // Paging
    implementation("androidx.paging:paging-runtime:$androidxPagingVersion")
    // optional - RxJava2 support
    implementation("androidx.paging:paging-rxjava2:$androidxPagingVersion")
    // optional - Guava ListenableFuture support
    implementation("androidx.paging:paging-guava:$androidxPagingVersion")

    // Room
    implementation("androidx.room:room-runtime:$androidxRoomVersion")
    kapt("androidx.room:room-compiler:$androidxRoomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$androidxRoomVersion")
    // optional - RxJava support for Room
    implementation("androidx.room:room-rxjava2:$androidxRoomVersion")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$androidxRoomVersion")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$androidxRoomVersion")

    // Work
    implementation("androidx.work:work-runtime-ktx:$androidxWorkVersion")
    // optional - RxJava2 support
    implementation("androidx.work:work-rxjava2:$androidxWorkVersion")
    // optional - GCMNetworkManager support
    implementation("androidx.work:work-gcm:$androidxWorkVersion")
    // optional - Test helpers
    androidTestImplementation("androidx.work:work-testing:$androidxWorkVersion")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    // optional - ViewModelInject Support
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:$hiltOptionalVersion")
    // optional - WorkerInject Support
    implementation("androidx.hilt:hilt-work:$hiltOptionalVersion")
    // optional - ViewModelInject & WorkerInject Compiler
    kapt("androidx.hilt:hilt-compiler:$hiltOptionalVersion")

    // Timber
    implementation("com.jakewharton.timber:timber:$timberVersion")

    // Okio
    implementation("com.squareup.okio:okio:$okioVersion")

    // Gson
    implementation("com.google.code.gson:gson:$gsonVersion")

    // ReactiveX
    implementation("io.reactivex.rxjava3:rxjava:$rxJava3Version")
    implementation("io.reactivex.rxjava3:rxkotlin:$rxKotlin3Version")
    implementation("io.reactivex.rxjava3:rxandroid:$rxAndroid3Version")

    // Ui
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$androidxConstraintLayoutVersion")
    implementation("androidx.viewpager2:viewpager2:$androidxViewpager2Version")

    // Test
    testImplementation("junit:junit:$junitVersion")

    // AndroidTest
    androidTestImplementation("androidx.test.ext:junit:$androidxJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidxEspressoVersion")
}
