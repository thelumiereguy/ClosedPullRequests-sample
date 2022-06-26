
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.thelumiereguy.data"
    compileSdk = configs.Configs.CompileSdk

    defaultConfig {
        minSdk = configs.Configs.MinSdk
        targetSdk = configs.Configs.TargetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

    implementation(configs.Coroutines.CoroutineCore)
    implementation(configs.Coroutines.CoroutineAndroid)

    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

    api(project(":helpers"))

    implementation(configs.Utilities.KotlinXSerializationJson)
    implementation(configs.SupportLibraries.CoreKtx)
    implementation(configs.SupportLibraries.Appcompat)

    implementation(configs.UILibraries.Material)

    testImplementation(kotlin("test"))

    testImplementation(configs.Coroutines.CoroutineTest)

    testImplementation(configs.TestUtils.JupiterJunit)

    testImplementation(configs.TestUtils.MockK)

    testImplementation("app.cash.turbine:turbine:0.8.0")

    api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
}
