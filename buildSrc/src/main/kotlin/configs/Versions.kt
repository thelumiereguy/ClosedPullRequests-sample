package configs

object Versions {
    const val KotlinXDateTime: String = "0.3.2"
    const val SlidingPanelLayout = "1.2.0"
    const val MockK = "1.12.2"
    const val Glide = "4.12.0"
    const val RecyclerView = "1.2.1"
    const val KotlinXSerialization = "1.3.3"

    const val ConstraintLayout = "2.1.4"
    const val MaterialDesign = "1.6.0"

    const val CoreKtx = "1.7.0"
    const val AppCompat = "1.4.1"
    const val LifeCycleRuntime = "2.4.1"
    const val ArchTesting = "2.1.0"
    const val Activity = "1.4.0"
    const val Fragment = "1.4.1"

    const val Coroutines = "1.6.2"

    const val Room = "2.4.2"

    const val AdapterDelegates = "4.3.2"

    const val FlowTurbine = "0.8.0"

    const val Retrofit = "2.9.0"
    const val OkHttpInterceptor = "4.9.2"

    const val KotlinXSerializationConverter = "0.8.0"
}

object SupportLibraries {
    const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
    const val Appcompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
    const val LifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LifeCycleRuntime}"
    const val LifecycleLivedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LifeCycleRuntime}"
    const val LifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LifeCycleRuntime}"
    const val LifecycleViewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LifeCycleRuntime}"
    const val ActivityKtx = "androidx.activity:activity-ktx:${Versions.Activity}"
    const val FragmentKtx = "androidx.fragment:fragment-ktx:${Versions.Fragment}"

    const val ArchTesting = "androidx.arch.core:core-testing:${Versions.ArchTesting}"
}

object UILibraries {
    const val SlidePanelLayout =
        "androidx.slidingpanelayout:slidingpanelayout:${Versions.SlidingPanelLayout}"

    const val ConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
    const val Material = "com.google.android.material:material:${Versions.MaterialDesign}"
    const val RecyclerView = "androidx.recyclerview:recyclerview:${Versions.RecyclerView}"
}

object Room {
    const val RoomRuntime = "androidx.room:room-runtime:${Versions.Room}"
    const val RoomCompiler = "androidx.room:room-compiler:${Versions.Room}"
    const val RoomKtx = "androidx.room:room-ktx:${Versions.Room}"
}

object Coroutines {
    const val CoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}"
    const val CoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Coroutines}"
    const val CoroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutines}"
}

object DaggerHiltLib {
    const val Android = "com.google.dagger:hilt-android:2.41"
    const val Compiler = "com.google.dagger:hilt-android-compiler:2.41"
}

object AdapterDelegates {
    const val AdapterDelegatesDsl =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.AdapterDelegates}"
    const val AdapterDelegatesViewBinding =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:${Versions.AdapterDelegates}"
}

object Utilities {
    const val KotlinXSerializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinXSerialization}"

    const val Glide = "com.github.bumptech.glide:glide:${Versions.Glide}"

    const val KotlinXDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.KotlinXDateTime}"
}

object Network {
    const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"

    const val OkhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttpInterceptor}"

    const val KotlinxSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.KotlinXSerializationConverter}"
}

object TestUtils {
    const val JupiterJunit = "org.junit.jupiter:junit-jupiter"
    const val MockK = "io.mockk:mockk:${Versions.MockK}"
    const val Turbine = "app.cash.turbine:turbine:${Versions.FlowTurbine}"
}
