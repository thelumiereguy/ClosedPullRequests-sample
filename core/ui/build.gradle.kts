
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.thelumiereguy.ui"
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
    }
}

dependencies {
    implementation(configs.SupportLibraries.CoreKtx)
    implementation(configs.SupportLibraries.Appcompat)
    implementation(configs.UILibraries.Material)
}
