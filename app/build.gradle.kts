plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = configs.Configs.CompileSdk
    namespace = "dev.thelumiereguy.trending_github_repos_sample"
    defaultConfig {
        applicationId = "dev.thelumiereguy.trending_github_repos_sample"
        minSdk = configs.Configs.MinSdk
        targetSdk = configs.Configs.TargetSdk
        versionCode = configs.Configs.VersionCode
        versionName = configs.Configs.VersionName
        testInstrumentationRunner = configs.Configs.AndroidJunitRunner
    }

    buildTypes {
        debug {
            isTestCoverageEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isCrunchPngs = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
    }
}

dependencies {

    implementation(configs.SupportLibraries.CoreKtx)
    implementation(configs.SupportLibraries.Appcompat)
    implementation(configs.Coroutines.CoroutineCore)
    implementation(configs.Coroutines.CoroutineAndroid)
    implementation(configs.SupportLibraries.LifecycleRuntime)
    implementation(configs.SupportLibraries.ActivityKtx)

    implementation(configs.UILibraries.Material)

    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
}
