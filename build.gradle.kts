import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "huadamus"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("com.github.sapher:youtubedl-java:2cbe41f")
    implementation("net.sf.trove4j:trove4j:3.0.3")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("com.googlecode.soundlibs:tritonus-share:0.3.7.4")
    implementation("com.googlecode.soundlibs:mp3spi:1.9.5.4")
    implementation("com.google.code.gson:gson:2.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            outputBaseDir.set(File("dist/"))
            targetFormats(TargetFormat.AppImage, TargetFormat.Exe)
            packageName = "YoutubeAutoStepper"
            packageVersion = "0.1.0"
        }
    }
}
