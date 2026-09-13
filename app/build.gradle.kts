import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Properties
import java.util.TimeZone

plugins {
    id("io.github.supermonster003.autojs6-native-alignment")
    id("org.autojs.build.utils")
    id("org.autojs.build.versions")
    id("org.autojs.build.jvm-convention")
    id("com.android.application")
}

val globalApplicationId = "io.github.supermonster003.autojs6.plugin.screencolorpicker"
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
    compileSdk = versions.sdkVersionCompile

    defaultConfig {
        applicationId = globalApplicationId
        minSdk = versions.sdkVersionMin
        targetSdk = versions.sdkVersionTarget
        versionCode = versions.appVersionCode
        versionName = versions.appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("string", "app_name", "Screen Color Picker")
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

    testImplementation(libs.junit)

    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.runner)
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

// Reject accidental native dependencies on every ABI.
nativeAlignment { expectNoNativeLibraries.set(true) }


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
tasks.named("appendDigestToReleasedFiles") { dependsOn(verifySignedReleaseArtifacts) }
tasks.matching { it.name == "prepareReleaseArtifacts" }.configureEach {
    dependsOn(verifySignedReleaseArtifacts)
}
