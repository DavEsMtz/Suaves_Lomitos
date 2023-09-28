// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val hiltVersion by extra("2.48")
    val retrofitVersion by extra("2.9.0")
    val moshiVersion by extra("1.15.0")
    val turbineVersion by extra("1.0.0")
    val coroutinesTestVersion by extra("1.6.4")
    val roomVersion by extra("2.5.2")
    val mockitoVersion by extra("5.1.0")
    repositories {
        google()
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}
