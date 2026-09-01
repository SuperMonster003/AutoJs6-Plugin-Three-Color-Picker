plugins {
    id("com.android.application") version System.getProperty("gradle.agp.version") apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
