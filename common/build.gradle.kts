plugins {
//    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("io.github.farshidroohi.library")
}

android {
    namespace = "io.github.farshidroohi.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.jetpackCompose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}