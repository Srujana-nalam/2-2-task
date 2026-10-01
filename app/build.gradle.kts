
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "example.task"
    compileSdk = 34

    defaultConfig {
        applicationId = "example.task"
        minSdk = 21
        targetSdk = 34
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("androidx.work:work-runtime-ktx:2.9.0")
}
