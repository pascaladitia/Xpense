# Xpense 💰

**Xpense** is a personal finance tracker built with **Kotlin Multiplatform (KMP)** + **Compose Multiplatform** and powered by **AI** to help you manage your income and expenses smarter.

## Demo

[▶️ Watch Demo Video](demo/demo.mp4)

---

## ✨ Features

- **Dashboard** — Real-time balance, income, and expense overview
- **Add Transaction** — Log income/expenses with category, date, and receipt photo
- **Analytics** — Category breakdown, monthly comparison, top spending
- **AI Finance Assistant** — Chat with AI (Llama 3.3 70B via Groq) for financial advice or logging transactions from natural language
- **AI Vision** — Snap a receipt and let AI read & record it
- **Cross-platform** — Runs on both Android and iOS with shared code

---

## 🧠 Why Xpense?

Xpense leverages **Kotlin Multiplatform** to share 100% of business logic and UI between Android & iOS, and **Groq AI** for a responsive finance assistant that understands natural language. Just say *"I spent $50 on food today"* and the AI logs it.

---

## 🛠 Tech Stack

| Tech | Description |
|---|---|
| **Kotlin Multiplatform** | 100% shared code Android & iOS |
| **Compose Multiplatform** | Modern declarative UI for both platforms |
| **Groq AI (Llama 3.3 70B)** | Smart finance assistant |
| **Ktor** | HTTP client for AI API |
| **Room** | Cross-platform local database |
| **Koin** | Dependency injection |
| **Coil** | Image loading |
| **BuildKonfig** | Build-time configuration |

---

## 🔑 API Key Configuration

Xpense uses **Groq AI API** (OpenAI-compatible) for the finance assistant feature. You need to provide your own API key.

1. Get a free API key from [console.groq.com](https://console.groq.com)
2. Create or edit `local.properties` in the project root:
   ```
   AI_API_KEY=gsk_your_api_key_here
   ```
3. The key is read at build time via **BuildKonfig** and injected into `BuildKonfig.AI_API_KEY`
4. `local.properties` is already in `.gitignore` — **never commit your API key**

> ⚠️ Without a valid API key, the AI chat feature will not work. Other features (dashboard, transactions, analytics) remain fully functional.

---

## 🚀 Getting Started

### Android
```bash
./gradlew :androidApp:assembleDebug
```
Then install the APK or run via Android Studio.

### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode, select the target, and hit Run.

---

## 📁 Project Structure

```
├── androidApp/          # Android entry point
├── iosApp/              # iOS entry point
├── sharedUI/            # Shared KMP code
│   └── src/
│       ├── commonMain/  # UI, ViewModel, Data, Domain
│       ├── androidMain/ # Android-specific implementations
│       └── iosMain/     # iOS-specific implementations
├── local.properties     # API key (git-ignored)
└── build.gradle.kts     # Root build config
```

---

## 📄 License

This project is built for learning and development purposes. Feel free to use and modify as needed.
