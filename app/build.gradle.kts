import android.annotation.SuppressLint

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.skyline.msgbot"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.skyline.msgbot"
        minSdk = 26
        @SuppressLint("ExpiredTargetSdkVersion")
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        multiDexEnabled = true
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
        isCoreLibraryDesugaringEnabled = true

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
            excludes += "org/graalvm/graphio/doc-files/diamond.png"
        }
    }
}

dependencies {

    implementation(project(":java-pkg"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")
    implementation(files("libs\\graal-truffle-compiler-libgraal.jar"))
    implementation(files("libs\\graal-processor.jar"))
    implementation(files("libs\\graal-management.jar"))
//    implementation(files("libs\\graal-graphio.jar"))
//    implementation(files("libs\\graal-libgraal-processor.jar"))
    implementation(files("libs\\graal-nativebridge-processor.jar"))
    implementation(files("libs\\locator.jar"))
//    implementation(files("libs\\compiler-22.3.1.jar"))
//    implementation(files("libs\\compiler-management-22.3.1.jar"))
    implementation(files("libs\\truffle-dsl-processor.jar"))
    implementation(files("libs\\truffle-nfi.jar"))
    implementation(files("libs\\truffle-nfi-libffi.jar"))
    implementation(files("libs\\graal-profdiff.jar"))
    implementation(files("libs\\graal.jar"))
    implementation(files("libs\\graal-truffle-jfr-impl.jar"))
    implementation(files("libs\\polyglot-tck.jar"))
    implementation(files("libs\\truffle-js-snapshot-tool.jar"))
//    implementation(files("libs\\truffle-js-factory-processor.jar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jsoup:jsoup:1.15.1")
    implementation("dev.tiangong:orhanobut-logger:2.2.3")
    implementation("com.caoccao.javet:javet-android:1.1.5")
    implementation("com.google.code.gson:gson:2.10.1")

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

    implementation(files("libs/java-json.jar"))

    implementation("com.jakewharton.android.repackaged:dalvik-dx:11.0.0_r3")

    implementation("com.google.android.material:material:1.8.0-rc01")

    implementation("com.google.protobuf:protobuf-java-util:3.21.12")

    implementation("org.greenrobot:greendao:3.3.0")

    /** desugaring */

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.0")

}