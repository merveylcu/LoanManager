plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.merveylcu.core"
}

dependencies {
    api(project(":core:common"))
    api(project(":core:theme"))
    api(project(":core:navigation"))
    api(project(":core:strings"))
}