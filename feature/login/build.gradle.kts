plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.merveylcu.feature.login"
}

dependencies {
    implementation(project(":feature:login:data"))
    implementation(project(":feature:login:domain"))
    api(project(":feature:login:presentation"))
}