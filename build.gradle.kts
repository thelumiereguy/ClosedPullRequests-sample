buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
        classpath(kotlin("serialization", version = "1.6.20"))
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.6.20-1.0.5" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.20" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

apply(from = file("./gradle_plugins/ktlint.gradle"))

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
