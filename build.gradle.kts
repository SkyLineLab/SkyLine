buildscript {
    val compose_version by extra("1.2.0-beta01")
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-alpha04" apply false
    id("com.android.library") version "8.1.0-alpha04" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}