import Versions.JAVA_VERSION
import Versions.JAVA_VERSION_STRING

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.keeply.data"

    setConfigs()
    defaultConfig {
        buildConfigField(
            "String",
            "BASE_URL",
            getBuildConfigProperty("BASE_URL")
        )
        consumerProguardFiles("consumer-rules.pro")
    }

    setBuildType()
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = JAVA_VERSION_STRING
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.bundles.retrofit)
    implementation(libs.serialization.json)

    hiltDependency()
}