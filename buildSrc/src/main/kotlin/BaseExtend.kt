import Versions.COMPILE_SDK
import Versions.MIN_SDK
import Versions.TARGET_SDK
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

fun BaseAppModuleExtension.setConfigs() {
    compileSdk = COMPILE_SDK

    defaultConfig {
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

fun BaseExtension.setConfigs() {
    compileSdkVersion(Versions.COMPILE_SDK)

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

fun BaseExtension.setBuildType() {
    buildTypes {
        getByName(BuildTask.DEBUG) {
            isDebuggable = BuildTaskDebug.isDebuggable
            isMinifyEnabled = BuildTaskDebug.isMinifyEnabled
        }
        getByName(BuildTask.RELEASE) {
            isDebuggable = BuildTaskRelease.isDebuggable
            isMinifyEnabled = BuildTaskRelease.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}