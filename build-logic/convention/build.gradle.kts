import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "io.github.farshidroohi.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("AndroidLibraryConventionPlugin") {
            id = "io.github.farshidroohi.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}