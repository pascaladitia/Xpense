@file:Suppress("DEPRECATION")

import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildKonfig)
}

android {
    namespace = "com.pascal.xpense.sharedUI"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    androidTarget {
        compilerOptions { jvmTarget = JvmTarget.JVM_17 }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            resources.srcDir("src/commonMain/composeResources")
        }

        commonMain.dependencies {
            api(libs.compose.runtime)
            api(libs.compose.ui)
            api(libs.compose.foundation)
            api(libs.compose.resources)
            api(libs.compose.ui.tooling.preview)
            api(libs.compose.material3)

            implementation(libs.material.icons.extended)
            implementation(libs.kermit)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.multiplatformSettings)
            implementation(libs.room.runtime)

            implementation(libs.moko.permission)
            implementation(libs.moko.permission.camera)
            implementation(libs.moko.permission.location)
            implementation(libs.moko.permission.notifications)
            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)
            implementation(libs.constraintlayout.compose.multiplatform)
            implementation(libs.peekaboo.ui)
            implementation(libs.peekaboo.image.picker)
            implementation(libs.sqlite.bundled)
            implementation(libs.compose.webview.multiplatform)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.androidx.customview.poolingcontainer)
            implementation(libs.emoji2)
            implementation(libs.emoji2.view.helper)
            implementation(libs.compose.multiplatform.media.player)
            implementation(libs.firebase.app)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.config)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.messaging)
            implementation(libs.compottie)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.compose.ui.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.ui.tooling)
            implementation(libs.androidx.ui.tooling.android)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.customview)
            implementation(libs.androidx.customview.poolingcontainer)
            implementation(libs.emoji2)
            implementation(libs.emoji2.view.helper)

            implementation(libs.itext7.core)
            implementation(libs.androidx.preference.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.room.runtime.android)
            implementation(libs.play.services.auth)
            implementation(libs.play.services.location)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries {
                framework {
                    baseName = "SharedUI"
                    binaryOption("bundleId", "com.pascal.xpense.sharedUI")
                    isStatic = true
                }
            }
        }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
}

val localProperties = Properties().apply {
    val localFile = File(rootDir, "local.properties")
    if (localFile.exists()) load(localFile.inputStream())
}
val aiApiKey = localProperties.getProperty("AI_API_KEY") ?: ""

buildkonfig {
    packageName = "com.pascal.xpense"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "https://www.url.com")
        buildConfigField(FieldSpec.Type.STRING, "AI_API_KEY", aiApiKey)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    with(libs.room.compiler) {
        add("kspAndroid", this)
        add("kspIosArm64", this)
        add("kspIosSimulatorArm64", this)
    }
}
