pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LoanManager"
include(":app")
include(":core")
include(":core:navigation")
include(":core:theme")
include(":core:common")
include(":component")
include(":feature")
include(":feature:loan")
include(":feature:loan:data")
include(":feature:loan:domain")
include(":feature:loan:presentation")
include(":feature:login")
include(":feature:login:data")
include(":feature:login:domain")
include(":feature:login:presentation")
include(":core:strings")
