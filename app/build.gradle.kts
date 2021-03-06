plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.skyline.msgbot"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.skyline.msgbot"
        minSdk = 21
        targetSdk = 29
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
        kotlinCompilerExtensionVersion = "1.2.0-alpha04"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material3:material3:1.0.0-alpha07")
    implementation(files("libs/gordjs.main.jar"))
    implementation(files("libs/graal-sdk.jar"))
    implementation(files("libs/graaljs-launcher.jar"))
    implementation(files("libs/graaljs.jar"))
    implementation(files("libs/graaljs-scriptengine.jar"))
    implementation(files("libs/launcher-common.jar"))
    implementation(files("libs/tregex.jar"))
    implementation(files("libs/truffle-api.jar"))
    implementation("com.google.code.gson:gson:2.9.0")
    implementation(project(mapOf("path" to ":java-pkg")))
    implementation("com.orhanobut:logger:2.2.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.rauschig:jarchivelib:1.2.0")
    // implementation("com.ibm.icu:icu4j:70.1")
    implementation("com.google.android.gms:play-services-wearable:17.1.0")
    implementation(files("libs/graal-dalvik.main.jar"))
    implementation(files("libs/wasm-launcher.jar"))
    implementation(files("libs/wasm.jar"))
    implementation(files("libs/truffle-sl-launcher.jar"))
    implementation(files("libs/truffle-sl.jar"))
    implementation("org.antlr:antlr4-runtime:4.10.1")
    implementation("net.bytebuddy:byte-buddy-android:1.12.10")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")

    // implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.6.10")
    // implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.6.10")
    // implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    // implementation("org.jetbrains.kotlin:kotlin-scripting-common:1.6.10")
    // implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:1.6.10")
    // implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:1.6.10")
    // implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.6.10")

    implementation("org.jsoup:jsoup:1.15.1")

    implementation("com.github.topjohnwu.libsu:core:4.0.3")
    implementation("com.github.topjohnwu.libsu:service:4.0.3")
    implementation("com.github.topjohnwu.libsu:io:4.0.3")
    implementation("com.github.topjohnwu.libsu:busybox:4.0.3")
}