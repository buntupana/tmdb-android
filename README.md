# tmdb-android

Native Android client for [The Movie Database (TMDB)](https://www.themoviedb.org/), built with Kotlin and Jetpack Compose.

The app lets users discover, search and explore movies, TV shows and people from TMDB, sign in with their TMDB account, and manage their favorites, watchlist, ratings and custom lists.

## Features

- **Discover** trending, popular and free-to-watch movies and TV shows with multiple filters.
- **Search** across movies, TV shows and people with suggestions and paginated results.
- **Media detail** screens for movies and TV shows with cast & crew, recommendations, seasons, images and external links (IMDb, Facebook, Instagram, X, TikTok).
- **Person detail** with filmography grouped by media type and department.
- **Account**: sign in via TMDB OAuth (Custom Tabs + deep link callback), session reload, sign out.
- **Lists**: favorites, watchlist, ratings and user-created lists — create, update and delete items.
- **Deep links** for `https://www.themoviedb.org` and the `app://buntupana.tmdb` scheme.

## Tech stack

- **Language / build**: Kotlin 2.3, AGP 9.2, JDK 21, Gradle version catalog (`gradle/libs.versions.toml`).
- **UI**: Jetpack Compose (BOM 2026.05), Material 3, Compose Navigation, Paging Compose, Accompanist, Coil 3, Palette.
- **Architecture**: multi-module Clean Architecture (`data` / `domain` / `presentation` / `di` per feature), MVI-style `ViewModel` + `State` + `Event`.
- **DI**: Koin (BOM 4.2).
- **Networking**: Ktor 3 (OkHttp engine) + kotlinx.serialization.
- **Persistence**: Room 2.8 + Paging, DataStore Preferences.
- **Other**: Timber, Moshi, Firebase (Analytics & Crashlytics NDK), Core Library Desugaring.
- **Min / target SDK**: 23 / 36.

## Module structure

```
app                         # Application module, navigation root, Koin startup
core/
  common                    # Shared models, providers, session management, BuildConfig (API key)
  data                      # Shared data sources, URL provider, Ktor/Room setup
  di                        # Common Koin module
  ui                        # Theme, navigation contracts, shared composables
feature/
  account/{data,domain,presentation,di}
  detail/{data,domain,presentation,di}
  discover/{data,domain,presentation,di}
  lists/{data,domain,presentation,di}
  search/{data,domain,presentation,di}
```

## Getting started

### Prerequisites

- Android Studio (latest stable) with the Android SDK installed.
- JDK 21.
- A TMDB account and API key — request one at <https://www.themoviedb.org/settings/api>.

### Configuration

1. Clone the repository.
2. Add your TMDB API key to `local.properties` at the project root:

   ```properties
   tmdb_api_key="YOUR_TMDB_API_KEY"
   ```

   The key is injected into `BuildConfig` via the [secrets-gradle-plugin](https://github.com/google/secrets-gradle-plugin) and consumed in `core/data` (`UrlProviderImpl.API_KEY`).
3. (Optional, for release builds) Replace `app/google-services.json` with your own Firebase project configuration if you want Crashlytics / Analytics to report to your account.

### Build & run

From Android Studio: open the project and run the `app` configuration on a device or emulator.

From the command line:

```bash
./gradlew :app:assembleDebug      # build a debug APK
./gradlew :app:installDebug       # install on a connected device/emulator
./gradlew test                    # run JVM unit tests
./gradlew connectedAndroidTest    # run instrumented tests
```

## License

See [LICENSE](LICENSE).
