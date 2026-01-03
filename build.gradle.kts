import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.vanniktech.maven.publish) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

    configure<KtlintExtension> {
        configure()
    }
}

configure<KtlintExtension> {
    configure()
}

fun KtlintExtension.configure() {
    version.set("1.3.1")
}
