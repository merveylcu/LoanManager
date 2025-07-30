plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.merveylcu.core.common"
}

dependencies {

    // Lifecycle & ViewModel
    implementation(libs.lifecycle.viewmodel)

    // SharedPreferences
    implementation(libs.security.crypto)

    // Hilt DI
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)
}