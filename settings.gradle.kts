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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

}

rootProject.name = "TMDB"
include (":app")
include(":core:ui")
include(":core:common")
include(":feature:account:data")
include(":feature:account:domain")
include(":feature:account:presentation")
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
include(":feature:account:di")
include(":core:data")
include(":core:di")
