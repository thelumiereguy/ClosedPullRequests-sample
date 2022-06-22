
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
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

    implementation(configs.Room.RoomRuntime)
    ksp(configs.Room.RoomCompiler)
    implementation(configs.Room.RoomKtx)
}
