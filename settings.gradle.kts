pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "TMDB"
include(":app")
include(":core:ui")
include(":core:common")
include(":feature:account:data")
include(":feature:account:domain")
include(":feature:account:presentation")
include(":feature:account:di")
include(":feature:detail:data")
include(":feature:detail:domain")
include(":feature:detail:presentation")
include(":feature:detail:di")
include(":feature:discover:data")
include(":feature:discover:domain")
include(":feature:discover:presentation")
include(":feature:discover:di")
include(":feature:search:domain")
include(":feature:search:data")
include(":feature:search:presentation")
include(":feature:search:di")
include(":core:data")
include(":core:di")
include(":feature:lists:data")
include(":feature:lists:domain")
include(":feature:lists:presentation")
include(":feature:lists:di")
include(":feature:seerr:data")
include(":feature:seerr:domain")
include(":feature:seerr:presentation")
include(":feature:seerr:di")
