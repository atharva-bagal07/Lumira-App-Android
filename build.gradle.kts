buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.3")
    }
}

plugins {
    id("com.android.application") version "8.2.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}