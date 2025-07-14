import Versions.JAVA_VERSION
import Versions.JAVA_VERSION_STRING

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.keeply.domain"

    setConfigs()
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    setBuildType()
    compileOptions {
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = JAVA_VERSION_STRING
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    hiltDependency()
}