import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        publishLibraryVariants("release")
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }
        
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
        }
        
        iosMain.dependencies {
        }
    }
}

android {
    namespace = "com.flaringapp.compose.topbar"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        aarMetadata {
            minCompileSdk = minSdk
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_reports")
    }
    
    // Configure source sets for Android
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

dependencies {
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    lintChecks(libs.slack.compose.linter)
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            androidVariantsToPublish = listOf("release"),
            sourcesJar = true,
            publishJavadocJar = true,
        )
    )
}
