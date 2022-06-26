plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.thelumiereguy.feature_closed_pr_list"
    compileSdk = configs.Configs.CompileSdk

    defaultConfig {
        minSdk = configs.Configs.MinSdk
        targetSdk = configs.Configs.TargetSdk

        testInstrumentationRunner = configs.Configs.AndroidJunitRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

    api(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(configs.SupportLibraries.CoreKtx)
    implementation(configs.SupportLibraries.Appcompat)
    implementation(configs.SupportLibraries.LifecycleRuntime)

    implementation(configs.UILibraries.Material)
    implementation(configs.UILibraries.SlidePanelLayout)

    implementation(configs.SupportLibraries.LifecycleViewModel)
    implementation(configs.SupportLibraries.LifecycleViewModelSavedState)

    implementation(configs.SupportLibraries.FragmentKtx)

    implementation(configs.AdapterDelegates.AdapterDelegatesDsl)
    implementation(configs.AdapterDelegates.AdapterDelegatesViewBinding)

    implementation(configs.Utilities.Glide)

    testImplementation(kotlin("test"))

    testImplementation(configs.Coroutines.CoroutineTest)

    testImplementation(configs.TestUtils.JupiterJunit)

    testImplementation(configs.TestUtils.MockK)
}
