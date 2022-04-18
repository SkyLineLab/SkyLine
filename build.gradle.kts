buildscript {
    val compose_version by extra("1.2.0-alpha05")
} // Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "7.3.0-alpha08" apply false
    id("com.android.library") version "7.3.0-alpha08" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.0.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}