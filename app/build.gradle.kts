plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.skyline.msgbot"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.skyline.msgbot"
        minSdk = 24
        targetSdk = 33
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
    sourceSets {
        getByName("main").run {
            java.srcDir("kotlin")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta01"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
    implementation("androidx.activity:activity-compose:1.5.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material3:material3:1.0.0-alpha14")
    implementation("com.google.code.gson:gson:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jsoup:jsoup:1.15.1")
    implementation("dev.tiangong:orhanobut-logger:2.2.3")
    implementation("com.caoccao.javet:javet-android:1.1.5")
    implementation("org.mozilla:rhino:1.7.14")

    /** Graal */
    implementation(files("libs/wasm-launcher.jar"))
    implementation(files("libs/wasm.jar"))
    implementation(files("libs/truffle-api.jar"))
    implementation(files("libs/tregex.jar"))
    implementation(files("libs/launcher-common.jar"))
    implementation(files("libs/graaljs-scriptengine.jar"))
    implementation(files("libs/graaljs-launcher.jar"))
    implementation(files("libs/graaljs.jar"))
    implementation(files("libs/graal-sdk.jar"))

    implementation(files("libs/adnopt.jar"))

    implementation(files("libs/graal-dalvik.jar"))

    implementation("com.google.android.material:material:1.7.0-alpha03")


}