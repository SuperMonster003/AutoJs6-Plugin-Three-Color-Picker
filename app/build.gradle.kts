import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Properties
import java.util.TimeZone
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

plugins {
    id("io.github.supermonster003.autojs6-native-alignment")
    id("org.autojs.build.utils")
    id("org.autojs.build.versions")
    id("org.autojs.build.jvm-convention")
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

val globalApplicationId = "io.github.supermonster003.autojs6.plugin.three.color.picker"
// Explicit local UI validation only: preserve the normal debug signer and its installed data.
val officialUiValidation = providers.gradleProperty("officialUiValidation").orNull == "true"
val releaseProperties = Properties().apply {
    rootProject.file("sign.properties").takeIf(File::isFile)?.inputStream()?.use { load(it) }
}
val releaseSigningAvailable = listOf("storeFile", "storePassword", "keyAlias", "keyPassword")
    .all { releaseProperties.getProperty(it).isNullOrBlank().not() }
    && releaseProperties.getProperty("storeFile")
        ?.let { file(it).takeIf(File::isFile) ?: rootProject.file(it) }
        ?.isFile == true
val buildDate = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).apply {
    timeZone = TimeZone.getTimeZone("GMT+08:00")
}.format(System.currentTimeMillis())

android {
    namespace = globalApplicationId
    bundle { language { enableSplit = false } }
    compileSdk = versions.sdkVersionCompile

    defaultConfig {
        applicationId = globalApplicationId
        minSdk = versions.sdkVersionMin
        targetSdk = versions.sdkVersionTarget
        versionCode = versions.appVersionCode
        versionName = versions.appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        if (officialUiValidation) testApplicationId = "$globalApplicationId.test.official"

        resValue("string", "app_name", "3-Color Picker")
        resValue("string", "plugin_author", "SuperMonster003")
        resValue("string", "plugin_version_date", buildDate)
    }

    signingConfigs {
        if (releaseSigningAvailable) {
            create("release") {
                storeFile = file(releaseProperties.getProperty("storeFile")).takeIf(File::isFile)
                    ?: rootProject.file(releaseProperties.getProperty("storeFile"))
                storePassword = releaseProperties.getProperty("storePassword")
                keyAlias = releaseProperties.getProperty("keyAlias")
                keyPassword = releaseProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            if (officialUiValidation) {
                check(releaseSigningAvailable) { "Official UI validation requires the existing release signing configuration" }
                signingConfig = signingConfigs.getByName("release")
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            if (releaseSigningAvailable) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    buildFeatures {
        aidl = true
        compose = true
        resValues = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(files("$rootDir/libs/common-plugin-api.aar"))
    implementation(files("$rootDir/libs/screen-color-picker-api.aar"))

    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.material)

    // Jetpack Compose, only for the release history page (AUTOJS6_PLUGIN_RELEASE_HISTORY_AGENTS.md); the BOM is the
    // single version source and also manages activity-compose.
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.animation)
    implementation(libs.compose.activity)

    testImplementation(libs.junit)

    // ui-test brings espresso-core 3.5.0, which fails to reflect InputManager.getInstance on API 36; 3.7.0 stays pinned.
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
}

val validateReleaseSigning = tasks.register("validateReleaseSigning") {
    group = "verification"
    description = "Fails when the signed release collection configuration is incomplete"
    doLast {
        check(releaseSigningAvailable) {
            "Release signing configuration is missing or incomplete; refusing to collect an unsigned APK"
        }
    }
}

tasks.configureEach {
    if (name == "assembleRelease") mustRunAfter(validateReleaseSigning)
}

tasks.register<Copy>("appendDigestToReleasedFiles") {
    group = "distribution"
    description = "Collects the signed release APK and appends its CRC32 digest"

    val extension = utils.FILE_EXTENSION_APK
    val sourceDirectory = layout.buildDirectory.dir("outputs/apk/release")
    val destinationDirectory = layout.projectDirectory.dir("releases")
    val expectedInputFile = "app-release.$extension"

    dependsOn(validateReleaseSigning, "assembleRelease")
    inputs.property("versionName", versions.appVersionName)
    inputs.property("releaseSigningAvailable", releaseSigningAvailable)
    outputs.upToDateWhen { false }

    doFirst {
        val actualInputFiles = sourceDirectory.get().asFile.listFiles { file ->
            file.isFile && file.extension == extension
        }.orEmpty().mapTo(mutableSetOf()) { it.name }
        check(actualInputFiles == setOf(expectedInputFile)) {
            "Expected exactly [$expectedInputFile], but found ${actualInputFiles.sorted()}"
        }
    }

    from(sourceDirectory)
    into(destinationDirectory)
    include(expectedInputFile)

    rename { name ->
        val releasedFileNamePrefix = "${rootProject.name}-v${versions.appVersionName}"
        val digest = utils.digestCRC32(sourceDirectory.get().file(name).asFile)
        "$releasedFileNamePrefix-$digest.$extension"
    }

    doLast { println("Destination: ${destinationDirectory.asFile}") }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

// Native code inventory: the plugin has no native code of its own, but Compose ui-graphics depends on
// androidx.graphics:graphics-path, whose libandroidx.graphics.path.so is packaged for four ABIs. The APK stays one
// universal package without ABI splits; the shared gate keeps the 16 KB alignment checks and writes SUPPORTED_ABIS,
// and verifyReleaseNativeLibraries below asserts that exactly these libraries are present and stored uncompressed.
val nativeLibraryAbis = listOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
val allowedNativeLibraries = nativeLibraryAbis.map { "lib/$it/libandroidx.graphics.path.so" }
nativeAlignment { expectNoNativeLibraries.set(false) }

val verifyReleaseNativeLibraries = tasks.register("verifyReleaseNativeLibraries") {
    group = "verification"
    description = "Checks that the release APK carries exactly the Compose graphics-path libraries, stored uncompressed"
    dependsOn("assembleRelease")
    val apkDir = layout.buildDirectory.dir("outputs/apk/release")
    doLast {
        val apks = apkDir.get().asFile.listFiles { file -> file.extension == "apk" }.orEmpty().toList()
        check(apks.size == 1) { "Expected one release APK, found ${apks.map { it.name }}" }
        ZipFile(apks.single()).use { zip ->
            val natives = zip.entries().asSequence().filter { it.name.startsWith("lib/") }.toList()
            check(natives.map { it.name }.sorted() == allowedNativeLibraries.sorted()) {
                "Unexpected native library set: ${natives.map { it.name }.sorted()}"
            }
            check(natives.all { it.method == ZipEntry.STORED }) { "Native libraries must be stored uncompressed" }
        }
    }
}


// Fail before collection when credentials, keystore or the actual APK set are incomplete.
val verifySignedReleaseArtifacts = tasks.register("verifySignedReleaseArtifacts") {
    group = "verification"
    dependsOn("assembleRelease")
    doLast {
        val signing = android.buildTypes.getByName("release").signingConfig
        check(signing != null && signing.storeFile?.isFile == true &&
            !signing.storePassword.isNullOrBlank() && !signing.keyAlias.isNullOrBlank() &&
            !signing.keyPassword.isNullOrBlank()) { "Release signing configuration is missing or incomplete" }
        val directory = layout.buildDirectory.dir("outputs/apk/release").get().asFile
        val apks = directory.listFiles { file -> file.isFile && file.extension == "apk" }.orEmpty()
        check(apks.map { it.name }.toSet() == setOf("app-release.apk")) {
            "Unexpected release APK set: ${apks.map { it.name }.sorted()}"
        }
        val buildTools = androidComponents.sdkComponents.sdkDirectory.get().asFile
            .resolve("build-tools/${android.buildToolsVersion}")
        val signerJar = buildTools.resolve("lib/apksigner.jar")
        check(signerJar.isFile) { "Android SDK apksigner is unavailable" }
        val result = providers.exec {
            commandLine("java", "-jar", signerJar.absolutePath, "verify", apks.single().absolutePath)
            isIgnoreExitValue = true
        }.result.get()
        check(result.exitValue == 0) { "Release APK signature verification failed" }
    }
}
tasks.named("appendDigestToReleasedFiles") { dependsOn(verifySignedReleaseArtifacts, verifyReleaseNativeLibraries) }
tasks.matching { it.name == "prepareReleaseArtifacts" }.configureEach {
    dependsOn(verifySignedReleaseArtifacts)
}
