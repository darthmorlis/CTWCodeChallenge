# CTW Android Code Challenge

Android application built as part of a technical code challenge.  
The app displays top news headlines from a predefined provider and allows the user to view article details.

---

## 📱 Features

- List of top headlines from a fixed news provider
- Articles ordered by most recent first
- Article detail screen with:
  - Image
  - Title
  - Description
  - Content
  - External link to full article
- Supports portrait and landscape orientations
- Clean architecture with separation of concerns (UI / ViewModel / Repository)
- Built using Jetpack Compose

---

## 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM |
| **Networking** | Retrofit 2 |
| **JSON Parsing** | Moshi |
| **Image Loading** | Coil |
| **Async** | Kotlin Coroutines + StateFlow |

---

## 🔑 API Key Setup

This project uses the [NewsAPI](https://newsapi.org).

For security reasons, the API key is **not included in the repository**.

### Steps to run the project:

1. Copy `local.properties.example` to `local.properties`
2. Add your NewsAPI key:

```properties
NEWS_API_KEY=YOUR_API_KEY_HERE
```

3. Sync the project and run the app

> 💡 The `local.properties` file is already in `.gitignore` and will not be committed.

---

## ▶️ How to Run

1. Clone the repository
2. Open the project in Android Studio (latest stable version)
3. Add your `NEWS_API_KEY` as described above
4. Run on an emulator or physical device (API 34+ recommended)

---

## 📐 Requirements Compliance

| Requirement | Status |
|-------------|--------|
| Written in Kotlin | ✅ |
| Targets latest Android API level | ✅ |
| Built using the latest Android Studio version | ✅ |
| Supports portrait & landscape modes | ✅ |
| Uses Retrofit and Moshi for networking | ✅ |
| Clean UI implemented with Jetpack Compose | ✅ |
| API keys excluded from version control | ✅ |

---

## 🧪 Testing

Unit tests are included for:

- ViewModels
- Repository logic
- Utility functions

Run tests using:

```bash
./gradlew test
```

---

## 📁 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/...    # Kotlin source files
│   │   └── res/            # Resources (layouts, drawables, values)
│   ├── test/               # Unit tests
│   └── androidTest/        # Instrumentation tests
├── build.gradle.kts        # App-level build config
└── proguard-rules.pro      # ProGuard rules
```

---

## 📄 License

This project was created as part of a technical code challenge.

