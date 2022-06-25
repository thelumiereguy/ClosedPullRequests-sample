package configs

object Configs {
    const val MinSdk = 21
    const val TargetSdk = 32
    const val CompileSdk = 32
    const val AndroidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val VersionCode = 1
    const val VersionName = "1.0"

    val FreeCompilerArgs = listOf(
        "-Xjvm-default=all",
        "-opt-in=kotlin.RequiresOptIn",
        "-opt-in=kotlin.Experimental",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.InternalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.FlowPreview",
        "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    )
}
