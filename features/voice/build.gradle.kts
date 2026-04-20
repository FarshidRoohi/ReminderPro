plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("io.github.farshidroohi.library")
}

android {
    namespace = "io.github.farshidroohi.voice"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}