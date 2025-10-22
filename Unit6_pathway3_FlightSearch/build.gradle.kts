// C:/Users/VINLAP/AndroidStudioProjects/FlightSearch/build.gradle.kts

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // You should also manage the ksp plugin version in the toml file
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false // Or move this to toml as well
}
