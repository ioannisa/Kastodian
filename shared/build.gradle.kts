import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    // add kotlin serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("maven-publish")
}

group = "eu.anifantakis.kastodian"
version = "0.0.2"

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
        publishLibraryVariants("release")  // Add this
    }

    // Define iOS targets
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    // Apply hierarchy template ONCE
    applyDefaultHierarchyTemplate()

    sourceSets {
        androidMain.dependencies {
              // data store preferences
        }
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.datastore.preferences)
            implementation("com.squareup.okio:okio:3.9.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

publishing {
    publications {
        val kotlinMultiplatformPublication = publications.getByName("kotlinMultiplatform") as MavenPublication
        kotlinMultiplatformPublication.groupId = group.toString()
        kotlinMultiplatformPublication.artifactId = "core"
        kotlinMultiplatformPublication.version = version.toString()
    }
    repositories {
        mavenLocal()
    }
}

android {
    namespace = "eu.anifantakis.kastodian"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}