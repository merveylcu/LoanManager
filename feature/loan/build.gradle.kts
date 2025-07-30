plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.merveylcu.feature.loan"
}

dependencies {
    implementation(project(":feature:loan:data"))
    implementation(project(":feature:loan:domain"))
    api(project(":feature:loan:presentation"))
}